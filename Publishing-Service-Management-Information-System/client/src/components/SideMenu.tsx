import {Box, createStyles, List, ListItem, ListItemButton, ListItemIcon, ListItemText, makeStyles} from "@mui/material";
import InboxIcon from '@mui/icons-material/Inbox';
import {useContext} from "react";
import {UserContext} from "../auth";
import {useNavigate} from "react-router-dom";

const SideMenu = () => {

    const [state, setState] = useContext(UserContext);
    const navigate = useNavigate()

    const handleLogout = () => {
        setState({data: null, loading: true, error: null});
        localStorage.removeItem("token");
        navigate("/");
    }

    return(
        <Box sx={{ width: '70%', maxWidth: 200, backgroundColor: 'lightgray' }}>
            <List>
                <ListItem disablePadding>
                    <ListItemButton onClick = {() => navigate("/account")}>
                        <ListItemIcon>
                            <InboxIcon />
                        </ListItemIcon>
                        <ListItemText primary="My account" />
                    </ListItemButton>
                </ListItem>
                <ListItem disablePadding>
                    <ListItemButton onClick={() => navigate("/dashboard")}>
                        <ListItemIcon>
                            <InboxIcon />
                        </ListItemIcon>
                        <ListItemText primary="My works" />
                    </ListItemButton>
                </ListItem>
                <ListItem disablePadding>
                    <ListItemButton onClick={() => navigate("/projects")}>
                        <ListItemIcon>
                            <InboxIcon />
                        </ListItemIcon>
                        <ListItemText primary="Projects" />
                    </ListItemButton>
                </ListItem>
                {
                    state.data?.role === "Publication Manager" && <ListItem disablePadding>
                        <ListItemButton onClick={() => navigate("/unmanagedWorks")}>
                            <ListItemIcon>
                                <InboxIcon />
                            </ListItemIcon>
                            <ListItemText primary="Unmanaged projects" />
                        </ListItemButton>
                    </ListItem>
                }
                <ListItem disablePadding>
                    <ListItemButton onClick={() => navigate("/users")}>
                        <ListItemIcon>
                            <InboxIcon />
                        </ListItemIcon>
                        <ListItemText primary="Users" />
                    </ListItemButton>
                </ListItem>
                <ListItem disablePadding>
                    <ListItemButton onClick ={handleLogout}>
                        <ListItemIcon>
                            <InboxIcon />
                        </ListItemIcon>
                        <ListItemText primary="Log Out" />
                    </ListItemButton>
                </ListItem>
            </List>
        </Box>
    )
}

export default SideMenu