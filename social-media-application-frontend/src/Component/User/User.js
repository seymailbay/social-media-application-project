import React from "react";
import {useParams} from "react-router-dom";
import Avatar from "../Avatar/Avatar";

function User(){

    const {userId} =useParams();
    return(
        <div>
            <Avatar avatarId ={0}/>
            <UserActivity/>
            
        </div>
    )

}

export default User;