import React, {useEffect, useState} from "react";
import Role from "../models/Role";
import axios from "axios";
import ApiUrl from "../config/api.config";
import {
    AlertColor,
    Button,
    Card,
    CardActions,
    CardContent,
    CardHeader,
    Container,
    FormControl,
    InputLabel, MenuItem, Select, SelectChangeEvent,
    TextField
} from "@mui/material";
import {useNavigate} from "react-router-dom";
import AlertMessage from "../components/AlertMessage";

interface UserResponse {
    message: String
}

const SignUpPage = () => {

    const navigate = useNavigate();
    const [username, setUsername] = useState<string>("");
    const [password, setPassword] = useState<string>("");
    const [email, setEmail] = useState<string>("");
    const [firstName, setFirstName] = useState<string>("");
    const [lastName, setLastName] = useState<string>("");
    const [role, setRole] = useState<string>("");
    const [roleSelection, setRoleSelection] = useState<Role[]>([]);
    const[showMessage, setShowMessage] = useState<boolean>(false);
    const [message, setMessage] = useState<string[]>([]);
    const [severity, setSeverity] = useState<AlertColor | undefined>("success");

    useEffect(() => {
        axios.get<Role[]>("http://localhost:8080/publishing-app/type/roles").then(response => setRoleSelection(response.data));
    }, [])

    const handleChange = (event: SelectChangeEvent) => {
        setRole(event.target.value as string);
    }

    const handleClick = async () => {
        await axios.post<UserResponse>(ApiUrl() + "access/signUp", {
            username,
            password,
            firstName,
            lastName,
            email,
            role
        }).then(() => {
            setUsername("");
            setPassword("");
            setFirstName("");
            setLastName("");
            setEmail("");
            setRole("");
            navigate("/")
        }).catch(error => {
            setMessage(error.response.data.message)
            setShowMessage(true);
            setSeverity("error");
        });
    }

    return <Container>
        <Card>
            <CardHeader title = "Sign Up"/>
            {
                showMessage && <AlertMessage severity={severity} message={message} setShowMessage={setShowMessage}/>
            }
            <CardContent>
                <TextField fullWidth label="Username" margin = "normal" onChange = {event => setUsername(event.target.value)}/>
                <TextField fullWidth label="Password" type = "password" onChange = {event => setPassword(event.target.value)}/>
                <TextField fullWidth label="Email" type = "email" margin = "normal" onChange = {event => setEmail(event.target.value)}/>
                <TextField fullWidth label="First Name" margin = "normal" onChange = {event => setFirstName(event.target.value)}/>
                <TextField fullWidth label="Last Name" margin = "normal" onChange = {event => setLastName(event.target.value)}/>
                <FormControl fullWidth>
                    <InputLabel>Role</InputLabel>
                    <Select onChange = {handleChange} value = {role}>
                        {
                            roleSelection.map(r => {
                                return <MenuItem value = {r.role}> {r.role} </MenuItem>
                            })
                        }
                    </Select>
                </FormControl>
            </CardContent>
            <CardActions>
                <Button size = "large" color = "primary" onClick = {handleClick}> Sign Up</Button>
            </CardActions>
        </Card>
    </Container>
}

export default SignUpPage