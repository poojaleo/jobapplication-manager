import {useLocation, useNavigate} from "react-router-dom";
import React from "react";
import AllJobApplications from "./AllJobApplications";

export function GetName(props) {
        let username = useLocation().state.username;
        /*if(username == null) {
            username = 'hermione';
        }*/
           // let username = "harrypotter";
            return <AllJobApplications {...props} username = {username} />;
}

