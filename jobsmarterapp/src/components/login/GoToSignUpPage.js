import { useNavigate } from 'react-router-dom';
import "./GoToSignUpPage.css";

export function GoToSignUpPage() {
    let navigate = useNavigate();
    const handleClick = e => {
        e.preventDefault();
        navigate('/signup');
    }
    return <button className={"signup-select"} onClick={handleClick}>Sign up</button>
}