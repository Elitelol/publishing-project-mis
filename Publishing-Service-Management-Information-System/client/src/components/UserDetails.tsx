import {useParams} from "react-router-dom";
import React, {useContext, useEffect, useState} from "react";
import {UserContext} from "../auth";
import User from "../models/User";
import axios from "axios";
import ApiUrl from "../config/api.config";
import Navbar from "./Navbar";
import SideMenu from "./SideMenu";
import {Box, Button, Container, TextField, Typography} from "@mui/material";
import UserDetailsNav from "./UserDetailsNav";
import NavMenu from "./NavMenu";

type NavProps = {
    id: any
    role: any
}

const UserDetails = ({id, role}: NavProps) => {
    const [context, setContext] = useContext(UserContext);
    const [user, setUser] = useState<User>({
        email: "",
        firstName: "",
        id: null,
        lastName: "",
        role: "",
        username: ""
    });

    const [email, setEmail] = useState<String>();
    const [firstName, setFirstName] = useState<String>();
    const [lastName, setLastName] = useState<String>();
    const [password, setPassword] = useState<String>("");
    const [passwordRepeated, setRepeatedPassword] = useState<String>("");
    const [showPassword, setShowPassword] = useState<boolean>(false);
    const [disabled, setDisabled] = useState<boolean>(false);

    useEffect(() => {
        axios.get<User>(ApiUrl() + "user/" + id).then(response => {
            setUser(response.data)
            setEmail(response.data.email)
            setFirstName(response.data.firstName)
            setLastName(response.data.lastName)
        });
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
        if (password !== "") {
            if (password === passwordRepeated) {
                axios.post(ApiUrl() + "user", {
                    id: user.id,
                    username: user.username,
                    email,
                    firstName,
                    lastName,
                    role: user.role,
                    password
                })
                handleClick();
            } else {

            }
        } else {
            axios.post(ApiUrl() + "user", {
                id: user.id,
                username: user.username,
                email,
                firstName,
                lastName,
                role: user.role
            })
        }
    }

    return (
        <>
            <NavMenu/>
            <Container>
                <Typography margin = "normal" variant ="h3"> {user.username} </Typography>
                {
                    role !== null && <UserDetailsNav id = {id} role={role}/>
                }
                <Typography margin = "normal" variant = "h5" >Details</Typography>
                <TextField margin = "normal" disabled fullWidth label="User Id" value = {user.id}/>
                <TextField margin = "normal" disabled={disabled} fullWidth label="User First Name" value = {firstName} onChange={event => setFirstName(event.target.value)}/>
                <TextField margin = "normal" disabled={disabled} fullWidth label="User Last Name" value = {lastName} onChange={event => setLastName(event.target.value)}/>
                <TextField margin = "normal" disabled={disabled} fullWidth label="User email" value ={email} onChange={event => setEmail(event.target.value)}/>
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
                    !disabled && <Button onClick ={handleSave}> Save </Button>
                }
            </Container>
        </>
    )
}

export default UserDetails;