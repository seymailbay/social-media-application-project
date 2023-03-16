import React, {useState, useRef, useEffect} from "react";
import { makeStyles } from '@material-ui/core/styles';
import clsx from 'clsx';
import Card from '@material-ui/core/Card';
import CardHeader from '@material-ui/core/CardHeader';
import CardMedia from '@material-ui/core/CardMedia';
import CardContent from '@material-ui/core/CardContent';
import CardActions from '@material-ui/core/CardActions';
import Collapse from '@material-ui/core/Collapse';
import Avatar from '@material-ui/core/Avatar';
import IconButton from '@material-ui/core/IconButton';
import Typography from '@material-ui/core/Typography';
import { blue } from '@material-ui/core/colors';
import FavoriteIcon from '@material-ui/icons/Favorite';
import ShareIcon from '@material-ui/icons/Share';
import ExpandMoreIcon from '@material-ui/icons/ExpandMore';
import MoreVertIcon from '@material-ui/icons/MoreVert';
import CommentIcon from '@material-ui/icons/Comment';
import {Link} from "react-router-dom"
import OutlinedInput from '@material-ui/core/OutlinedInput'
import Button from "@material-ui/core/Button";
import  { InputAdornment } from "@material-ui/core";
import Snackbar from '@material-ui/core/Snackbar';
import MuiAlert from '@material-ui/lab/Alert';

function Alert(props){
    return <MuiAlert elevation={6} varient="filled" {...props} />;
}

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
        backgroundColor: '#3e73b8',
    },
    link: {
        textDecoration : "none",
        boxShadow : "none",
        color : "white"
    }
}));

function PostForm(props){
    const {userId,userName,refreshPost} =props;
    const classes =useStyles();
    const [expanded, setExpanded] =useState(false);  //Başlangıçta açılır kapanır buton false olacak. yorum kısmı
    const [liked,setLiked] = useState(false);
    const [text, setText ] =useState("");
    const [title, setTitle] = useState("");
    const [isSent, setIsSent] = useState(false);

    const savePost = () => {
        fetch("http://localhost:8083/posts",{
                method: "POST", 
                headers: {
                    "Content-Type": "application/json",
                    "Authorization" :localStorage.getItem("tokenKey"),
                }, 
                body: JSON.stringify({
                    title: title, 
                    userId: userId, 
                    text: text,
                }),
            })
            .then((res) => res.json())
            .catch((err) => console.log("error"))
    }


    const handleLike = () =>{
    setLiked(!liked);
    }


    const handleSubmit = () => {
        // Send a POST request to the server with the post data
        savePost();
        setIsSent(true);
        setTitle("");
        setText("");
        refreshPost();
    }

    const handleTitle = (value) => {
        setTitle(value);
        setIsSent(false);
    }
    const handleText =  (value) => {
        setText(value);
        setIsSent(false);
    }
    const handleClose = (event, reason) => {
        if (reason === 'clickaway') {
            return;
        }

        setIsSent(false);
    };



    return(
        <div>
            <Snackbar open={isSent} autoHideDuration={600} onClose={handleClose}>
                <Alert onClose={handleClose} severity="success" sx={{ width: '100%' }}>
                   Successful !
                </Alert>
            </Snackbar>
            <Card className={classes.root}>
                <CardHeader
                    avatar={
                        <Link className ={classes.link} to={"/users/" + userId}>
                            <Avatar aria-label="recipe" className={classes.avatar}>
                                {userName.charAt(0).toUpperCase()}
                            </Avatar>
                        </Link>
                    }
                    title={
                        <OutlinedInput
                            id="outlined-adornment-amount"
                            multiline
                            placeholder="Title"
                            inputProps={{maxLength: 250}}
                            fullWidth
                            value = {title}
                            onChange = { (i) => handleTitle(i.target.value)}
                        >
                        </OutlinedInput>}
                />
                <CardContent>
                    <Typography variant="body2" color="textSecondary" component="p">
                        <OutlinedInput
                            id ="outlined-adornment-amount"
                            multiline
                            placeholder ="Text"
                            inputProps ={{maxLenght: 250}}
                            fullWidth
                            value = {text}
                            onChange ={ (i) => handleText( i.target.value)}
                            endAdornment ={
                                <InputAdornment position ="end" >
                                    <Button
                                        variant = "contained"
                                        style = {{background: 'linear-gradient(45deg, #2196F3 30%,#21CBF3 90%)',
                                            color: 'white'}}
                                        onClick ={handleSubmit}
                                    >Post</Button>
                                </InputAdornment>
                            }
                        >
                        </OutlinedInput>
                    </Typography>
                </CardContent>
                <CardActions disableSpacing>
                    <IconButton
                        onClick={handleLike}
                        aria-label="add to favorites">
                        <FavoriteIcon style ={liked ? {color:"#3e73b8"} :null } />
                    </IconButton>
                </CardActions>
            </Card>
        </div>
    )
}

export default PostForm;