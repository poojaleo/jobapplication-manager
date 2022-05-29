import React, {useState, useEffect}  from "react";
import {Route, BrowserRouter as Router, Routes} from "react-router-dom";
import './App.css';
import Home from "./components/Home/Home";
import Login from "./components/login/Login";
import Signup from "./components/login/Signup"
import AllJobApplications from "./components/jobapplication/AllJobApplications";
import {GetName} from "./components/jobapplication/GetName";
import { GetNameForQuestions } from "./components/question/GetNameForQuestions";
import AllQuestions from "./components/question/AllQuestions";
import AuthService from "./components/services/AuthService";


function App() {

    const [username, setUsername] = useState(AuthService.getCurrentUsername);
    const [isUsernameSet, setUsernameStatus] = useState(false);

    useEffect(() => {
        userAuthState();
    })

    const userAuthState = () => {
        const currentUsername = AuthService.getCurrentUsername();
        if(currentUsername === undefined || currentUsername === null) {
            setUsername(currentUsername);
            setUsernameStatus(false);
        } else {
            setUsername(currentUsername);
            setUsernameStatus(true);
        }
    }


        return (
            /*<Router className={"App"}>
                <Routes>
                    <Route path='/' element={<Home/>} />
                    <Route path='/home' element={<Home/>} />
                    <Route path='/login' element={<Login/>} />
                    <Route path='/signup' exact={true} element={<Signup/>} />
                    <Route path='/jobapplications' element={<GetName/>} />
                    <Route path ='/questions' element={<GetNameForQuestions />} />
                </Routes>
            </Router>*/
            <div>
                <Routes>
                    {isUsernameSet && (
                        <>
                            <Route path={"/"} element={<Home />} />
                            <Route path={"/home"} element={<Home />} />
                            <Route path={"/login"} element={<Login authenticate = {() => userAuthState()} />} />
                            <Route path={"/signup"} element={<Signup />} />
                            <Route path={"/jobapplications"} element={<AllJobApplications />} />
                            <Route path={"/questions"} element={<AllQuestions />} />
                        </>
                    )}

                    {!isUsernameSet && (
                        <>
                            <Route path={"/"} element={<Home />} />
                            <Route path={"/home"} element={<Home />} />
                            <Route path={"/login"} element={<Login authenticate = {() => userAuthState()} />} />
                            <Route path={"/signup"} element={<Signup />} />
                            <Route path={"/jobapplications"} element={<Home />} />
                            <Route path={"/questions"} element={<Home />} />
                        </>
                    )}
                </Routes>
            </div>

        )

}

export default App;
