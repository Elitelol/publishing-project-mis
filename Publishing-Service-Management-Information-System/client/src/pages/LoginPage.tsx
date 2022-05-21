import {AlertColor, Button, Card, CardActions, CardContent, CardHeader, Container, TextField} from "@mui/material";
import React, {useContext, useState} from "react";
import {useNavigate} from "react-router-dom";
import {UserContext} from "../auth";
import axios from "axios";
import ApiUrl from "../config/api.config";
import {toast, ToastContainer} from "react-toastify";
import AlertMessage from "../components/AlertMessage";

const LoginPage = () => {

    interface loginResponse {
        token: string,
        id: number,
        role: string
    }

    const [username, setUsername] = useState<string>("");
    const [password, setPassword] = useState<string>("");
    const navigate = useNavigate();
    const [context, setContext] = useContext(UserContext);
    const[showMessage, setShowMessage] = useState<boolean>(false);
    const [message, setMessage] = useState<string[]>([]);
    const [severity, setSeverity] = useState<AlertColor | undefined>("success");


    const handleClick = async () => {
        await axios.post<loginResponse>(ApiUrl() + "access/login", {
            username,
            password
        }).then(response => {
            setContext({data:{id: response.data.id, role: response.data.role }, loading: false, error: null})
            localStorage.setItem("token", response.data.token);
            axios.defaults.headers.common["auth"] = `Bearer ${response.data.token}`;
            navigate("/dashboard");
        }).catch(error => {
            setMessage(error.response.data.message)
            setShowMessage(true);
            setSeverity("error");
        });
    }

    const redirectSignUp = () => {
        navigate("/signup")
    }

    return <>
        <Container>
            <Card>
                <CardHeader title = "Publishing House Project Management System"/>
                {
                    showMessage && <AlertMessage severity={severity} message={message} setShowMessage={setShowMessage}/>
                }
                <CardContent>
                    <TextField fullWidth label="Username" margin = "normal" onChange = {event => setUsername(event.target.value)}/>
                    <TextField fullWidth label="Password" type = "password" onChange = {event => setPassword(event.target.value)}/>
                </CardContent>
                <CardActions>
                    <Button size = "large" color = "primary" onClick = {handleClick}> Login</Button>
                    <Button size = "large" color = "secondary" onClick = {redirectSignUp}> Sign Up</Button>
                </CardActions>
            </Card>
        </Container>
    </>
};

export default LoginPage;