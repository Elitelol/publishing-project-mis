import React, {useEffect, useState} from "react";
import Publication from "../models/Publication";
import axios from "axios";
import ApiUrl from "../config/api.config";
import Navbar from "../components/Navbar";
import SideMenu from "../components/SideMenu";
import {Container} from "@mui/material";
import Publications from "../components/Publications";
import NavMenu from "../components/NavMenu";

const ProjectsPage = () => {
    const [publications, setPublications] = useState<Publication[]>([]);

    useEffect(() => {
        axios.get(ApiUrl() + "publication/all").then(res => {
            setPublications(res.data)
        });
    }, [])

    return(
        <>
            <NavMenu/>
            <Container >
                <Publications publications = {publications} publicationText={"Publishing House Projects"}/>
            </Container>
        </>
    )
}

export default ProjectsPage