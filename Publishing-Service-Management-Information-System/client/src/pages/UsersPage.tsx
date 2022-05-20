import Navbar from "../components/Navbar";
import SideMenu from "../components/SideMenu";
import React, {useEffect, useState} from "react";
import {
    Button,
    Container,
    Paper, Table,
    TableBody,
    TableCell,
    TableContainer,
    TableHead,
    TableRow,
    Typography
} from "@mui/material";
import axios from "axios";
import User from "../models/User";
import ApiUrl from "../config/api.config";
import UserRow from "../components/UserRow";
import NavMenu from "../components/NavMenu";

const UsersPage = () => {

    const [showAuthors, setShowAuthors] = useState<boolean>(true);
    const [showManagers, setShowManagers] = useState<boolean>(false);
    const [showWorkers, setShowWorkers] = useState<boolean>(false);
    const [authors, setAuthors] = useState<User[]>([]);
    const [managers, setManagers] = useState<User[]>([]);
    const [workers, setWorkers] = useState<User[]>([]);

    useEffect(() => {
        axios.get<User[]>(ApiUrl() + "user/authors").then(response => setAuthors(response.data));
        axios.get<User[]>(ApiUrl() + "user/managers").then(response => setManagers(response.data));
        axios.get<User[]>(ApiUrl() + "user/workers").then(response => setWorkers(response.data));
    }, [])

    const handleAuthors = () => {
        setShowAuthors(true);
        setShowManagers(false);
        setShowWorkers(false);
    }

    const handleManagers = () => {
        setShowAuthors(false);
        setShowManagers(true);
        setShowWorkers(false);
    }

    const handleWorkers = () => {
        setShowAuthors(false);
        setShowManagers(false);
        setShowWorkers(true);
    }


    return (
        <>
            <NavMenu/>
            <Container>
                <Typography variant = "h2">Registered users</Typography>
                <Button onClick={handleAuthors}>Authors</Button>
                <Button onClick={handleManagers}>Publication Managers</Button>
                <Button onClick={handleWorkers}>Workers</Button>
                <TableContainer component = {Paper}>
                    <Table sx = {{minWidth: 650}}>
                        <TableHead>
                            <TableRow>
                                <TableCell align ="right">User Id</TableCell>
                                <TableCell align ="right">User username</TableCell>
                                <TableCell align ="right">User First Name</TableCell>
                                <TableCell align ="right">User Last Name</TableCell>
                                <TableCell align ="right">Role</TableCell>
                            </TableRow>
                        </TableHead>
                        <TableBody>
                            {
                                showAuthors && authors.map(user => {
                                    return <UserRow value = {user}/>
                                })
                            }
                            {
                                showManagers && managers.map(user => {
                                    return <UserRow value = {user}/>
                                })
                            }
                            {
                                showWorkers && workers.map(user => {
                                    return <UserRow value = {user}/>
                                })
                            }
                        </TableBody>
                    </Table>
                </TableContainer>
            </Container>
        </>
    )
}

export default UsersPage