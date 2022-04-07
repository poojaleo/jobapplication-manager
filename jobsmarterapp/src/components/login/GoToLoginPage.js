import { useNavigate } from 'react-router-dom';
import "./GoToLoginPage.css";

export function GoToLoginPage() {
    let navigate = useNavigate();
    const handleClick = e => {
        e.preventDefault();
        navigate('/login');
    }
    return <button className={"login-select"} onClick={handleClick}>Login</button>
}