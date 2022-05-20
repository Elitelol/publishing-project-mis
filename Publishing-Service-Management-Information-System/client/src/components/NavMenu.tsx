import {Box} from "@mui/material";
import Navbar from "./Navbar";
import SideMenu from "./SideMenu";

const NavMenu = () => {
    return(
        <Box sx={{maxHeight:110}}>
            <Navbar/>
            <SideMenu/>
        </Box>
    )
}

export default NavMenu