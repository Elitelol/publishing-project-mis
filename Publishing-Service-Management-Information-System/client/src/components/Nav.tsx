import {Link, useNavigate} from "react-router-dom";
import {useContext} from "react";
import {UserContext} from "../auth";

const Nav = () => {
    const [state, setState] = useContext(UserContext);
    const navigate = useNavigate()

    const handleLogout = () => {
        setState({data: null, loading: true, error: null});
        localStorage.removeItem("token");
        navigate("/");
    }

    return <>

    </>

};

export default Nav