import {useNavigate} from "react-router-dom";

const JobApplicationsButton = () => {
    const navigate = useNavigate();

    const routeToJobApplicationsPage = () => {
        let path = '/jobapplications';
        navigate(path);
    }

    return (
        <div>

            <button className={"login-select mx-2"} onClick={routeToJobApplicationsPage}>Job Applications</button>
        </div>
    )
}

export default JobApplicationsButton;