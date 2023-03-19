import React, {useEffect, useState} from "react";
import Post from '../Post/Post';
import {makeStyles} from '@material-ui/core/styles';
import Container from '@material-ui/core/Container';
import Box from '@material-ui/core/Box';
import PostForm from '../Post/PostForm';

const useStyles = makeStyles((theme) => ({
    container: {
        display: "flex",
        flexWrap: "wrap",
        justifyContent: "center",
        alignItems: "center",
        backgroundColor: "rgba(248,60,0,0.55)",
    }
}));

function Home(){

    const [error,setError] = useState(null);
    const [isLoaded,setIsLoaded] = useState(false);
    const [postList,setPostList] =useState([]);
    const classes = useStyles();

    const refreshPost = () => {

        fetch("http://localhost:8083/posts")
            .then(res => res.json())
            .then(
                (result) => {
                    setIsLoaded(true);
                    setPostList(result);
                },
                (error) => {
                    setIsLoaded(true)
                    setError(error)
                }
            )
    }

    useEffect(() =>{
        refreshPost()
    })

    if(error){
        return <div>
            Error !!
        </div>
    }
    else if(!isLoaded){
        return <div>
            Loading...
        </div>
    }
    else{
        return(
            <Container fixed className={classes.container}>
                {localStorage.getItem("currentUser") == null ? "":  <PostForm  userId={localStorage.getItem("currentUser")} userName={localStorage.getItem("userName")} refreshPost ={refreshPost} />}
                {postList.map(post => (
                    <Post likes={post.postLikes} postId={post.id} userId={post.userId} userName={post.userName} title={post.title} text={post.text}></Post>
                ))}
            </Container>
        );
    }
}
export default Home;