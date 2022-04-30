import {Button, Card, CardActions, CardContent, CardHeader, Container, TextField} from "@mui/material";
import {useContext, useState} from "react";
import {useNavigate} from "react-router-dom";
import {UserContext} from "../auth";
import axios from "axios";
import ApiUrl from "../config/api.config";

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


    const handleClick = async () => {
        try {
            const response = await axios.post<loginResponse>(ApiUrl() + "access/login", {
                username,
                password
            });
            setContext({data:{id: response.data.id, role: response.data.role }, loading: false, error: null})
            localStorage.setItem("token", response.data.token);
            axios.defaults.headers.common["auth"] = `Bearer ${response.data.token}`;
            navigate("/dashboard");
        } catch (error) {

        }
    }

    const redirectSignUp = () => {
        navigate("/signup")
    }

    return <>
        <Container>
            <Card>
                <CardHeader title = "Publishing Management"/>
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