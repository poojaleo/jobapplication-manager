import React from "react";
import jobsmarterLogo from "../Home/jobsmarter_logo.png";
import "./UserCreateNavbar.css";
import {GoToLoginPage} from "../login/GoToLoginPage";

class UserCreateNavbar extends React.Component {
    render() {
        return (
            <div className={"user"}>
                <div className={"user top-bar"}>
                    <a href="/home">
                        <h5>Home</h5>
                    </a>
                    <h5>About Us</h5>
                    <div className={"user user-select"}>
                        <GoToLoginPage />
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

export default UserCreateNavbar;