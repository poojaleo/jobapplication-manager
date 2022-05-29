import { useNavigate } from 'react-router-dom';
import "./GoToLoginPage.css";

export function GoToLoginPage() {
    /*let navigate = useNavigate();
    const handleClick = e => {
        e.preventDefault();
        navigate('/login');
    }
    return <button className={"login-select"} onClick={handleClick}>Login</button>*/
    const navigate = useNavigate();

    const routeToLoginPage = () => {
        let path = '/login';
        navigate(path);
    }

    return (
        <div>
            <button className={"login-select mx-2"} type={"submit"} onClick={routeToLoginPage}>Login</button>
        </div>
    )
}