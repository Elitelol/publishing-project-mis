import {Button, ButtonGroup} from "@mui/material";
import {useNavigate} from "react-router-dom";

const NavigationGroup = ({id}: any) => {

    const navigate = useNavigate();

    return (
        <ButtonGroup>
            <Button onClick = {() => navigate("/publication/" + id)}> Details </Button>
            <Button onClick = {() => navigate("/" + id + "/attachments")}> Attachments </Button>
            <Button onClick = {() => navigate("/" + id + "/contract")}> Contract </Button>
            <Button onClick = {() => navigate("/" + id + "/budget")}> Budget </Button>
            <Button onClick = {() => navigate("/" + id + "/task")}> Tasks </Button>
        </ButtonGroup>
    )
}

export default NavigationGroup