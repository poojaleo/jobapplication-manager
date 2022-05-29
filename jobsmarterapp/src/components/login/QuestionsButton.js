import {useNavigate} from "react-router-dom";

const QuestionsButton = () => {
    const navigate = useNavigate();

    const routeToQuestionsPage = () => {
        let path = '/questions';
        navigate(path);
    }

    return (
        <div>

            <button className={"login-select mx-2"} onClick={routeToQuestionsPage}>Interview Preparation</button>
        </div>
    )
}

export default QuestionsButton;