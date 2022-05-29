import React from "react";
import jobsmarterLogo from "../Home/jobsmarter_logo.png";
import "./UserCreateNavbar.css";
import {GoToLoginPage} from "../login/GoToLoginPage";

class UserCreateNavbar extends React.Component {
    render() {
        return (
            <div className={"user"}>
                <div className={"d-flex flex-row justify-content-end"}>
                    <div className={"mt-2"}>
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