import {useNavigate} from "react-router-dom";
import {Button, ButtonGroup} from "@mui/material";

type NavProps = {
    id: any
    role: any
}

const UserDetailsNav = ({id, role} : NavProps) => {
    const navigate = useNavigate();

    return (
        <ButtonGroup>
            <Button onClick = {() => navigate("/" + role + "/" + id)}> Details </Button>
            <Button onClick = {() => navigate("/" + role + "/" + id + "/works")}> Works </Button>
        </ButtonGroup>
    )
}

export default UserDetailsNav;