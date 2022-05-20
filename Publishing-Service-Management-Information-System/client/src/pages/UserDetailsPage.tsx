import Navbar from "../components/Navbar";
import SideMenu from "../components/SideMenu";
import React, {useContext, useEffect, useState} from "react";
import {useParams} from "react-router-dom";
import {UserContext} from "../auth";
import User from "../models/User";
import {Box, Button, Container, TextField, Typography} from "@mui/material";
import axios, {AxiosResponse} from "axios";
import ApiUrl from "../config/api.config";

const UserDetailsPage = () => {

    const {id} = useParams()
    const [context, setContext] = useContext(UserContext);
    const [user, setUser] = useState<User>({
        email: "",
        firstName: "",
        id: null,
        lastName: "",
        role: "",
        username: ""
    });

    const [userId, setUserId] = useState<number | null>();
    const [username, setUsername] = useState<String>();
    const [email, setEmail] = useState<String>();
    const [firstName, setFirstName] = useState<String>();
    const [lastName, setLastName] = useState<String>();
    const [role, setRole] = useState<String>();
    const [password, setPassword] = useState<String>("");
    const [passwordRepeated, setRepeatedPassword] = useState<String>("");
    const [showPassword, setShowPassword] = useState<boolean>(false);
    const [disabled, setDisabled] = useState<boolean>(false);

    useEffect(() => {
        axios.get<User>(ApiUrl() + "user/" + id).then(response => setUser(response.data));
        if (typeof id === "string") {
            setDisabled(parseInt(id) !== context.data?.id);
        }
    },[])

    const handleClick = () => {
        setPassword("");
        setRepeatedPassword("");
        setShowPassword(false);
    }

    const handleSave = () => {

    }

    return (
        <>
            <Navbar/>
            <SideMenu/>
            <Container>
                <Typography margin = "normal" variant ="h3"> {user.username} </Typography>
                <Typography margin = "normal" variant = "h5" >Details</Typography>
                <TextField margin = "normal" disabled fullWidth label="User Id" value = {user.id}/>
                <TextField margin = "normal" disabled={disabled} fullWidth label="User First Name" value = {user.firstName}/>
                <TextField margin = "normal" disabled={disabled} fullWidth label="User Last Name" value = {user.lastName}/>
                <TextField margin = "normal" disabled={disabled} fullWidth label="User email" value ={user.email}/>
                <TextField margin = "normal" disabled fullWidth label="User Role" value = {user.role}/>
                {
                    !disabled && !showPassword && <Button onClick={() => setShowPassword(!showPassword)}> Change password </Button>
                }
                {
                    showPassword && <Box>
                        <Typography margin = "normal" variant = "h6">New password</Typography>
                        <TextField type="password" margin = "normal" fullWidth label="New password" value ={password} onChange={event => setPassword(event.target.value)}/>
                        <TextField type="password" margin = "normal" fullWidth label="New password repeated" value ={passwordRepeated} onChange={event => setRepeatedPassword(event.target.value)}/>
                        <Button onClick = {handleClick}>Close</Button>
                    </Box>
                }
                {
                    !disabled && <Button> Save </Button>
                }
            </Container>
        </>
    )
}

export default UserDetailsPage