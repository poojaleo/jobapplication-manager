import React from "react";
import jobsmarterLogo from "../Home/jobsmarter_logo.png";
import "./JobApplicationNavbar.css";


// let usernameToPass = "";
//
// function QuestionClick(name) {
//
//     let navigate = useNavigate();
//     const routeChange = (event) => {
//         event.preventDefault();
//         navigate('/questions', {
//             state:{username:GetNameForNavbar.username}
//         });
//     }
//     return <button onClick={routeChange} className={"questionsButton"}>Interview Preparation</button>
// }

class JobApplicationNavbar extends React.Component {

    // constructor(props) {
    //     super(props);
    //
    //     this.state = {
    //         username: this.props.username,
    //     }
    //
    //     usernameToPass = this.state.username;
    // }

    render() {
        return (
            <div className={"top-bar"}>
                <a href="/home">
                    <div className={"logo"}>
                        <img src={jobsmarterLogo} alt={"company logo"}/>
                    </div>
                </a>
                {/*<QuestionClick />*/}
                <a href="/home">
                    <button className={"logout"}>Log Out</button>
                </a>
            </div>
        );
    }
}

export default JobApplicationNavbar;