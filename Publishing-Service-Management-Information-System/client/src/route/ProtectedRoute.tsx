import {useContext} from "react";
import {UserContext} from "../auth";
import {Navigate, Outlet} from "react-router-dom";

const ProtectedRoute = () => {
    const [state, setState] = useContext(UserContext);

    if (state.loading) {
        return <Navigate to={"/"}/>
    }
    return state.data ? <Outlet/> : <Navigate to={"/"}/>
}

export default ProtectedRoute;