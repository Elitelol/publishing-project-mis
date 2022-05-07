import React, {useEffect, useState} from "react";
import Publication from "../models/Publication";
import axios from "axios";
import ApiUrl from "../config/api.config";
import Navbar from "../components/Navbar";
import SideMenu from "../components/SideMenu";
import {Container} from "@mui/material";
import Publications from "../components/Publications";

const UnmanagedWorksPage = () => {
    const [publications, setPublications] = useState<Publication[]>([]);

    useEffect(() => {
        axios.get(ApiUrl() + "publication/unmanaged").then(res => {
            setPublications(res.data)
        });
    }, [])

    return(
        <>
            <Navbar/>
            <SideMenu/>
            <Container >
                <Publications publications = {publications} publicationText={"Unmanaged publications"}/>
            </Container>
        </>
    )
}

export default UnmanagedWorksPage