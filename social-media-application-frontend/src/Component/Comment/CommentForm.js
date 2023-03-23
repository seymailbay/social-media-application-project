import React, {useState} from "react";
import {Link} from "react-router-dom";
import { makeStyles } from '@material-ui/core/styles';
import { CardContent, InputAdornment, OutlinedInput, Avatar} from "@material-ui/core";
import Button from "@material-ui/core/Button";
import SendIcon from "@material-ui/icons/Send";


const useStyles = makeStyles((theme) => ({
    comment : {
        display : "flex",
        flexWrap: "wrap",
        justifyContent: "flex-start",
        alignItems : "center",
    },
    small:{
        width: theme.spacing(4),
        height: theme.spacing(4),
    },
    link: {
        textDecoration :"none",
        boxShadow: "none",
        color: "white"
    }
}));

function  CommentForm(props){
    const {userId,userName, postId} = props;
    const classes = useStyles();
    const [text, setText]=useState("");

    const saveComment = () => {
        fetch("http://localhost:8083/comments", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                "Authorization": localStorage.getItem("tokenKey"),
            },
            body: JSON.stringify({
                postId: postId,
                userId: localStorage.getItem("currentUser"),
                text: text,
            }),
        })
            .then((res) => {
                if (!res.ok) {
                    throw new Error("Network response was not ok");
                }
                return res.json();
            })
            .then((data) => {
                console.log(data); // Yanıtı konsola yazdır
            })
            .catch((err) => {
                console.log("error:", err); // Hata mesajını konsola yazdır
            });
    };

    const handleSubmit =() =>{
        saveComment();
        setText("");
    }
    const handleChange=(value) => {
        setText(value);
    }
    
    return(
        <CardContent className = {classes.comment}>

            <OutlinedInput
                id="outlined-adornment-amount"
                multiline
                inputProps={{maxLength: 250}}
                fullWidth
                onChange={(i) => handleChange(i.target.value)}
                startAdornment={
                    <InputAdornment position="start">
                        <Link className={classes.link} to={{ pathname: '/users/' + userId }}>
                            {userName && (
                                <Avatar aria-label="recipe" className={classes.small}>
                                    {userName.charAt(0).toUpperCase()}
                                </Avatar>
                            )}
                        </Link>
                    </InputAdornment>
                }
                style ={{color :"black", backgroundColor: 'white'}}
                endAdornment = {
                <InputAdornment position ="end">
                    <Button
                        variant = "contained"
                        style = {{background: 'linear-gradient(45deg, #2196F3 30%,#21CBF3 90%)',
                            color: 'white'}}
                        onClick ={handleSubmit}
                        endIcon={<SendIcon></SendIcon>}
                    >PUSH</Button>
                </InputAdornment>
                }
                value={text}
            ></OutlinedInput>
        </CardContent>
    )
}

export default CommentForm;

