import React, {useContext, useEffect, useState} from "react";
import Publication from "../models/Publication";
import {UserContext} from "../auth";
import axios from "axios";
import ApiUrl from "../config/api.config";
import {
    Container
} from "@mui/material";
import Publications from "../components/Publications";
import {useNavigate} from "react-router-dom";
import NavMenu from "../components/NavMenu";

const DashboardPage = () => {

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
            url = "publication/worker/";
        }
        return await axios.get(ApiUrl() + url + userId);
    }

    return (
        <>
            <NavMenu/>
            <Container >
                <Publications publications = {publications} publicationText={"My publication works"}/>
            </Container>
        </>
    )
}

export default DashboardPage