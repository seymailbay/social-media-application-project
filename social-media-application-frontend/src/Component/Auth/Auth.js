import React, {useState} from "react";
import {FormControl, InputLabel,Input, Button,FormHelperText} from "@material-ui/core"

function Auth(){

    const [username,setUsername] = useState("")
    const [password,setPassword] =useState("")

    const handleUsername =(value) =>{
        setUsername(value)
    }

    const handlePassword = (value) => {
        setPassword(value)
    }

    const sendRequest =(path) => {
        fetch("http://localhost:8083/auth/"+path,{
            method:"POST",
            headers:{
                "Content-Type":"application/json",
                "Authorization" :localStorage.getItem("tokenKey"),
            },
            body : JSON.stringify({
                userName: username,
                password: password,
            })
        })
            .then((res) => res.json())
            .then((result)=>{
                            localStorage.setItem("tokenKey",result.message);
                            localStorage.setItem("currentUser",result.userId);
                            localStorage.setItem("userName",username);
            })
            .catch((err) => console.log(err))
    };

    // Artık istek gönderildiği için username passwordler sıfırlanır.
        const handleRegister = () =>{
        sendRequest("register")
        setUsername("")
        setPassword("")
    }


    const handleLogin = () =>{
        sendRequest("login")
        setUsername("")
        setPassword("")
        console.log(localStorage)
    }

    const handleButton =(path) => {
            sendRequest(path)
            setUsername("")
            setPassword("")
            console.log(localStorage)
    }

    return(
        <FormControl>
            <InputLabel>Username</InputLabel>
            <Input  onChange = {(i) => handleUsername(i.target.value)}/>
            <InputLabel  style={{top: 80}}>Password</InputLabel>
            <Input  style={{top: 40}}
                    type ="password"
                    onChange = {(i) => handlePassword(i.target.value)}/>
            <Button variant = "contained"
                    style = {{marginTop : 60,
                        background :'linear-gradient(45deg, #2196F3 30%, #21CBF3 90%)',
                        color : 'white'}}
                    onClick= {() => handleButton("register")}>Register</Button>
            <FormHelperText style={{margin:20}}>Are you already registered?</FormHelperText>
            <Button variant = "contained"
                    style = {{
                        background :'linear-gradient(45deg, #2196F3 30%, #21CBF3 90%)',
                        color : 'white'}}
                    onClick={() => handleButton("login")}>Login</Button>

        </FormControl>
    /*
            <FormControl>
                <InputLabel> Username </InputLabel>
                <Input onChange={(i) => handleUsername(i.target.value)} />
                <InputLabel style={{top: 80}}> Password </InputLabel>
                <Input style ={{top :40}}
                       type="password"
                    onChange={(i) => handlePassword(i.target.value)}/>
                <Button
                    variant="contained"
                    style={{
                        marginTop: 60,
                        background: "linear-gradient(45deg, #2196F3 30%, #21CBF3 90%)",
                        color: "white" }}
                    onClick={()=>handleButton("register")}>Register</Button>
                <FormHelperText style={{ margin: 20 }}>
                    Are you already registered?
                </FormHelperText>
                <Button
                    variant="contained"
                    style={{
                        background: "linear-gradient(45deg, #2196F3 30%, #21CBF3 90%)",
                        color: "white" }}
                    onClick={ () => handleButton("login")}>Login</Button>
            </FormControl>*/
    )
        /*
        return(
        <FormControl>
            <InputLabel>Username</InputLabel>
            <Input  onChange = {(i) => handleUsername(i.target.value)}/>
            <InputLabel  style={{top: 80}}>Password</InputLabel>
            <Input  style={{top: 40}}
            onChange = {(i) => handlePassword(i.target.value)}/>
            <Button variant = "contained"
                style = {{marginTop : 60,
                background :'linear-gradient(45deg, #2196F3 30%, #21CBF3 90%)',
                color : 'white'}}
                onClick= {() => handleButton("register")}>Register</Button>
            <FormHelperText style={{margin:20}}>Are you already registered?</FormHelperText>
            <Button variant = "contained"
                style = {{
                background :'linear-gradient(45deg, #2196F3 30%, #21CBF3 90%)',
                color : 'white'}}
                onClick={() => handleButton("login")}>Login</Button>

        </FormControl>
         */
    
}

export default Auth;