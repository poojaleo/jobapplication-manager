import React from "react";
import {Route, BrowserRouter as Router, Routes} from "react-router-dom";
import './App.css';
import Home from "./components/Home/Home";
import Login from "./components/login/Login";
import Signup from "./components/login/Signup"
import AllJobApplications from "./components/jobapplication/AllJobApplications";
import {GetName} from "./components/jobapplication/GetName";
import { GetNameForQuestions } from "./components/question/GetNameForQuestions";
import AllQuestions from "./components/question/AllQuestions";


class App extends React.Component {
    render() {
        return (
            <Router className={"App"}>
                <Routes>
                    <Route path='/' element={<Home/>} />
                    <Route path='/home' element={<Home/>} />
                    <Route path='/login' element={<Login/>} />
                    <Route path='/signup' exact={true} element={<Signup/>} />
                    <Route path='/jobapplications' element={<GetName/>} />
                    <Route path ='/questions' element={<GetNameForQuestions />} />
                </Routes>
            </Router>

        )
    }
}

export default App;
