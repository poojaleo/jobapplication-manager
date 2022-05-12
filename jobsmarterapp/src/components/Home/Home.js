import React from "react";
import "./Home.css";
import JobSmarterNavbar from "../NavBar/JobSmarterNavbar";
import {Link, useNavigate } from "react-router-dom";
import jobhomepage from "./jobhomepage.png";

class Home extends React.Component {

    render() {

        /*let navigate = useNavigate();
        function handleClick() {
            navigate('/home')
        }*/

        return (
            <div className={"home"}>
                <div className={"aboutus"}>
                    <JobSmarterNavbar />
                    <div className={"info"}>
                        <div className={"infosection"}>
                            <h4>
                                Never lose track of your applications again</h4>
                            <div className={"d-flex flex-row justify-content-around"}>
                                <p>With JobSmarter, you can easily monitor the current state of all your job applications at a glance.
                                    JobSmarter also helps to organize and prepare for your interview by storing all your
                                    interview questions in one place.</p>
                                <div className={"homeImage"}>
                                    <img src={jobhomepage} alt={"home page icon"}/>
                                </div>
                            </div>

                        </div>
                    </div>
                </div>
            </div>
        )
    }
}

export default Home;