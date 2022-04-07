import React from "react";
import jobsmarterLogo from "../Home/jobsmarter_logo.png";
import {GoToSignUpPage} from "../login/GoToSignUpPage";
import "./UserNavbar.css";

class UserNavbar extends React.Component {
    render() {
        return (
            <div className={"user"}>
                <div className={"user top-bar"}>
                    <a href="/home">
                        <h5>Home</h5>
                    </a>
                    <h5>About Us</h5>
                    <div className={"user user-select"}>
                        <GoToSignUpPage />
                    </div>
                    <a href="/home">
                        <div className={"user logo"}>
                            <img src={jobsmarterLogo} alt={"company logo"}/>
                        </div>
                    </a>
                </div>
            </div>
        );
    }
}

export default UserNavbar;