import React, {useContext, useEffect, useState} from "react";
import Publication from "../models/Publication";
import {UserContext} from "../auth";
import axios from "axios";
import ApiUrl from "../config/api.config";
import {
    Box, Button,
    Container, LinearProgress, LinearProgressProps,
    Paper,
    Table,
    TableBody,
    TableCell,
    TableContainer,
    TableHead,
    TableRow,
    Typography
} from "@mui/material";
import Navbar from "../components/Navbar";
import Publications from "../components/Publications";
import {useNavigate} from "react-router-dom";
import SideMenu from "../components/SideMenu";

const DashboardPage = () => {

    const navigate = useNavigate();
    const [context, setContext] = useContext(UserContext);
    const [publications, setPublications] = useState<Publication[]>([]);

    useEffect(() => {
        fetchPublications().then(res => {
            setPublications(res.data)
        });
    }, [])

    const fetchPublications = async () => {
        let url: string
        let role = context.data?.role;
        let userId = context.data?.id
        if (role === "Author") {
            url = "publication/author/";
        } else if (role === "Publication Manager") {
            url = "publication/manager/";
        } else {
            url = "publication/byWorker/";
        }
        return await axios.get(ApiUrl() + url + userId);
    }

    return (
        <>
            <Navbar/>
            <SideMenu/>
            <Container >
                <Publications publications = {publications} publicationText={"My publication works"}/>
            </Container>
        </>
    )
}

export default DashboardPage