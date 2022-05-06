import {Container} from "@mui/material";
import Navbar from "../components/Navbar";
import SideMenu from "../components/SideMenu";
import React from "react";
import NavigationGroup from "../components/NavigationGroup";
import {useParams} from "react-router-dom";

const AttachmentPage = () => {
    const {id} = useParams()

    return(
        <>
            <Navbar/>
            <SideMenu/>
            <Container>
                <NavigationGroup id = {id}/>
                {"attachments"}
            </Container>
        </>
    )
}

export default AttachmentPage