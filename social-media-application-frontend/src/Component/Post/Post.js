import React, {useState, useRef, useEffect} from "react";
import { makeStyles } from '@material-ui/core/styles';
import clsx from 'clsx';
import Card from '@material-ui/core/Card';
import CardHeader from '@material-ui/core/CardHeader';
import CardMedia from '@material-ui/core/CardMedia';
import CardContent from '@material-ui/core/CardContent';
import CardActions from '@material-ui/core/CardActions';
import Collapse from '@material-ui/core/Collapse';
import Container from '@material-ui/core/Container';
import Avatar from '@material-ui/core/Avatar';
import IconButton from '@material-ui/core/IconButton';
import Typography from '@material-ui/core/Typography';
import { red } from '@material-ui/core/colors';
import FavoriteIcon from '@material-ui/icons/Favorite';
import ShareIcon from '@material-ui/icons/Share';
import ExpandMoreIcon from '@material-ui/icons/ExpandMore';
import MoreVertIcon from '@material-ui/icons/MoreVert';
import CommentIcon from '@material-ui/icons/Comment';
import {Link} from "react-router-dom"
import Comment from "../Comment/Comment";
import CommentForm from "../Comment/CommentForm";

const useStyles = makeStyles((theme) => ({
    root: {
       width: 800,
        textAlign:'left',
        margin : 15
    },
    media: {
        height: 0,
        paddingTop: '56.25%', // 16:9
    },
    expand: {
        transform: 'rotate(0deg)',
        marginLeft: 'auto',
        transition: theme.transitions.create('transform', {
            duration: theme.transitions.duration.shortest,
        }),
    },
    avatar: {
        backgroundColor: '#428ced',
    },
    link: {
        textDecoration : "none",
        boxShadow : "none",
        color : '#f1f2eb'
    }
}));

function Post(props){
    const {title,text, userId,userName,postId,likes} =props;
    const classes =useStyles();
    const [expanded, setExpanded] =useState(false);  //Başlangıçta açılır kapanır buton false olacak. yorum kısmı
    const [error, setError] =useState(null);
    const [isLoaded, setIsLoaded] = useState(false);
    const [commentList,setCommentList] =useState([]);
    const [liked,setLiked] = useState(false);
    const isInitialMount = useRef(true);
    const [isLiked, setIsLiked]=useState(false);
    const [likeCount,setLikeCount]=useState(likes.length);
    const [likeId,setLikeId]=useState(null);
    const [refresh, setRefresh] = useState(false);
    let disabled =localStorage.getItem("currentUser") ==null ? true:false
   const setCommentRefresh = () => {
        setRefresh(true);
    }

    const handleExpandClick = () => {
        setExpanded(!expanded);
        refreshComments();
        console.log(commentList);
    };

    const handleLike =() => {
        setIsLiked(!isLiked);
        if(!isLiked){
            saveLike();
            setLikeCount(likeCount+1)
        }
        else{
            deleteLike();
            setLikeCount(likeCount-1)
        }
    }

    const refreshComments = () => {

        fetch("http://localhost:8083/comments?postId=" +postId)
            .then(res => res.json())
            .then(
                (result) => {
                    setIsLoaded(true);
                    setCommentList(result);
                },
                (error) => {
                    setIsLoaded(true)
                    setError(error)
                }
            )
        setRefresh(false);
    }
    const saveLike = () => {
        fetch("http://localhost:8083/Likes",{
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                "Authorization" :localStorage.getItem("tokenKey"),
            },
            body: JSON.stringify({
                postId: postId,
                userId: localStorage.getItem("currentUser")
            }),
        })
            .then((res) => res.json())
            .catch((err) => console.log("error"))
    }

    const deleteLike = () => {
        fetch("http://localhost:8083/Likes/"+likeId,{
            method: "DELETE",
            headers: {
                "Authorization" :localStorage.getItem("tokenKey"),
            },
        })
            .catch((err) => console.log("error"))
    }

const checkLikes = () => {
  var likeControl= likes.find((like=>""+ like.userId===localStorage.getItem("currentUser")));
  if(likeControl!=null){
      setLikeId(likeControl.id);
      setIsLiked(true);
  }
}
    useEffect(() => {
        if(isInitialMount.current)
            isInitialMount.current=false;
        else
            refreshComments();
    })

    useEffect(()=> {checkLikes()},[])
    return(
            <Card className={classes.root}>
                <CardHeader
                    avatar={
                        <Link  className={classes.link} to={{pathname : '/users/' + userId}}>
                            <Avatar aria-label="recipe" className={classes.avatar}>
                                {userName?.charAt(0).toUpperCase()}
                        </Avatar>
                        </Link>
                    }
                    title={title}
                />
                <CardContent>
                    <Typography variant="body2" color="textSecondary" component="p">
                        {text}
                    </Typography>
                </CardContent>
                <CardActions disableSpacing>
                    {disabled ?
                        <IconButton
                            disabled
                            onClick={handleLike}
                            aria-label="add to favorites">
                            <FavoriteIcon style={isLiked? { color: "#03a1fc" } : null} />
                        </IconButton> :
                        <IconButton
                            onClick={handleLike}
                            aria-label="add to favorites">
                            <FavoriteIcon style={isLiked? { color: "#03a1fc" } : null} />
                        </IconButton>
                    }
                    {likeCount}
                    <IconButton
                        className={clsx(classes.expand, {
                            [classes.expandOpen]: expanded,
                        })}
                        onClick={handleExpandClick}
                        aria-expanded={expanded}
                        aria-label="show more"
                    >
                        <CommentIcon />
                    </IconButton>
                </CardActions>
                        <Collapse in={expanded} timeout="auto" unmountOnExit>
                        <Container fixed className = {classes.container}>
                            {error ? "error" :
                            isLoaded? commentList.map(comment => (
                                <Comment userId = {comment.userId} userName={comment.userName} text={comment.text}></Comment>
                            )) : "Loading" }
                            {disabled? "":
                        <CommentForm userId = {localStorage.getItem(userId)} userName={localStorage.getItem("userName")} postId={postId}></CommentForm>}
                        </Container>
                        </Collapse>
            </Card>
    )
}

export default Post;