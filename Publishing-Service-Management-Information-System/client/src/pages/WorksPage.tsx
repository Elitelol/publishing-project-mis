import {useParams} from "react-router-dom";
import UserDetailsNav from "../components/UserDetailsNav";
import React, {useEffect, useState} from "react";
import Publication from "../models/Publication";
import axios from "axios";
import ApiUrl from "../config/api.config";
import Publications from "../components/Publications";
import Navbar from "../components/Navbar";
import SideMenu from "../components/SideMenu";
import {Container} from "@mui/material";
import NavMenu from "../components/NavMenu";

const WorksPage = () => {
    const {role, id} = useParams();
    const [publications, setPublications] = useState<Publication[]>([]);

    useEffect(() => {
        if (role === "Author") {
            axios.get<Publication[]>(ApiUrl() + "publication/author/" + id).then(response => setPublications(response.data));
        } else if(role === "Worker") {
            axios.get<Publication[]>(ApiUrl() + "publication/worker/" + id).then(response => setPublications(response.data));
        } else {
            axios.get<Publication[]>(ApiUrl() + "publication/manager/" + id).then(response => setPublications(response.data));
        }
    }, [])

    return(
        <>
            <NavMenu/>
            <Container>
                <UserDetailsNav id ={id} role={role}/>
                <Publications publications={publications} publicationText={"User works"}/>
            </Container>
        </>
    )
}

export default WorksPage