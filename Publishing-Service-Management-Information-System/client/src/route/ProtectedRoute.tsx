import {useContext} from "react";
import {UserContext} from "../auth";
import {Navigate, Outlet} from "react-router-dom";
import {CircularProgress} from "@mui/material";

const ProtectedRoute = () => {
    const [state, setState] = useContext(UserContext);

    if (state.loading) {
        return <CircularProgress />
    }
    return state.data ? <Outlet/> : <Navigate to={"/"}/>
}

export default ProtectedRoute;