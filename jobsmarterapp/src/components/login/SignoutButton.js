import {useNavigate} from "react-router-dom";
import AuthService from "../services/AuthService";
import {Button} from "react-bootstrap";

const SignoutButton = () => {
    const navigate = useNavigate();

    const routeToHomePage = () => {
        let path = '/home';
        AuthService.resetUserSession();
        navigate(path);
    }

    return (
        <div>
            <button className={"login-select mx-2"} type={"submit"} onClick={routeToHomePage}>Sign Out</button>
        </div>
    )
}

export default SignoutButton;