import {Button, ButtonGroup} from "@mui/material";
import {useNavigate} from "react-router-dom";

type NavigationProps = {
    id: any,
    unallowedToClick: boolean
    unallowedAttach: boolean
}

const NavigationGroup = ({id, unallowedToClick, unallowedAttach}: NavigationProps) => {

    const navigate = useNavigate();

    return (
        <ButtonGroup>
            <Button onClick = {() => navigate("/publication/" + id)}> Details </Button>
            <Button disabled={unallowedAttach} onClick = {() => navigate("/" + id + "/attachments")}> Attachments </Button>
            <Button disabled={unallowedToClick} onClick = {() => navigate("/" + id + "/contract")}> Contract </Button>
            <Button disabled={unallowedToClick} onClick = {() => navigate("/" + id + "/budget")}> Budget </Button>
            <Button disabled={unallowedToClick} onClick = {() => navigate("/" + id + "/task")}> Tasks </Button>
        </ButtonGroup>
    )
}

export default NavigationGroup