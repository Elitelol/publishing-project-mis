import React, {useContext, useEffect, useState} from "react";
import {UserContext} from "../auth";
import User from "../models/User";
import axios from "axios";
import ApiUrl from "../config/api.config";
import {AlertColor, Box, Button, Container, TextField, Typography} from "@mui/material";
import UserDetailsNav from "./UserDetailsNav";
import NavMenu from "./NavMenu";
import AlertMessage from "./AlertMessage";

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
    const[showMessage, setShowMessage] = useState<boolean>(false);
    const [message, setMessage] = useState<string[]>([]);
    const [severity, setSeverity] = useState<AlertColor | undefined>("success");

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

    const handleSave = async () => {
        if (password !== "") {
            if (password === passwordRepeated) {
                await axios.post(ApiUrl() + "user", {
                    id: user.id,
                    username: user.username,
                    email,
                    firstName,
                    lastName,
                    role: user.role,
                    password
                }).then(() => {
                    setMessage(["Account details saved."]);
                    setShowMessage(true);
                    setSeverity("success");
                    handleClick();
                }).catch(error => {
                    setMessage(error.response.data.message)
                    setShowMessage(true);
                    setSeverity("error");
                })
            } else {
                setMessage(["Passwords don't match."])
                setShowMessage(true);
                setSeverity("error");
            }
        } else {
            await axios.post(ApiUrl() + "user", {
                id: user.id,
                username: user.username,
                email,
                firstName,
                lastName,
                role: user.role
            }).then(() => {
                setMessage(["Account details saved."]);
                setShowMessage(true);
                setSeverity("success");
            }).catch(error => {
                setMessage(error.response.data.message)
                setShowMessage(true);
                setSeverity("error");
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
                {
                    showMessage && <AlertMessage severity={severity} message={message} setShowMessage={setShowMessage}/>
                }
                <TextField margin = "normal" disabled fullWidth label="User Id" value = {user.id} InputLabelProps={{ shrink: user.id ? true : false }} />
                <TextField margin = "normal" disabled={disabled} fullWidth label="User First Name" value = {firstName} onChange={event => setFirstName(event.target.value)} InputLabelProps={{ shrink: firstName? true : false }}/>
                <TextField margin = "normal" disabled={disabled} fullWidth label="User Last Name" value = {lastName} onChange={event => setLastName(event.target.value)} InputLabelProps={{ shrink: lastName ? true : false }}/>
                <TextField margin = "normal" disabled={disabled} fullWidth label="User email" value ={email} onChange={event => setEmail(event.target.value)} InputLabelProps={{ shrink: email ? true : false }}/>
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