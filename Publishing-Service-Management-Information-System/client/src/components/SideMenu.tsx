import {Box, createStyles, List, ListItem, ListItemButton, ListItemIcon, ListItemText, makeStyles} from "@mui/material";
import InboxIcon from '@mui/icons-material/Inbox';
import AccountBoxIcon from '@mui/icons-material/AccountBox';
import ListIcon from '@mui/icons-material/List';
import LibraryBooksIcon from '@mui/icons-material/LibraryBooks';
import GroupIcon from '@mui/icons-material/Group';
import LogoutIcon from '@mui/icons-material/Logout';
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
        <Box sx={{ width: '100', maxWidth: 200}}>
            <List>
                <ListItem disablePadding divider={true}>
                    <ListItemButton onClick = {() => navigate("/account")}>
                        <ListItemIcon>
                            <AccountBoxIcon/>
                        </ListItemIcon>
                        <ListItemText primary="My account" />
                    </ListItemButton>
                </ListItem>
                <ListItem disablePadding divider={true}>
                    <ListItemButton onClick={() => navigate("/dashboard")}>
                        <ListItemIcon>
                            <LibraryBooksIcon/>
                        </ListItemIcon>
                        <ListItemText primary="My works" />
                    </ListItemButton>
                </ListItem>
                <ListItem disablePadding divider={true}>
                    <ListItemButton onClick={() => navigate("/projects")}>
                        <ListItemIcon>
                            <ListIcon/>
                        </ListItemIcon>
                        <ListItemText primary="Projects" />
                    </ListItemButton>
                </ListItem>
                {
                    state.data?.role === "Publication Manager" && <ListItem disablePadding divider={true}>
                        <ListItemButton onClick={() => navigate("/unmanagedWorks")}>
                            <ListItemIcon>
                                <InboxIcon />
                            </ListItemIcon>
                            <ListItemText primary="Unmanaged projects" />
                        </ListItemButton>
                    </ListItem>
                }
                <ListItem disablePadding divider={true}>
                    <ListItemButton onClick={() => navigate("/users")}>
                        <ListItemIcon>
                            <GroupIcon/>
                        </ListItemIcon>
                        <ListItemText primary="Users" />
                    </ListItemButton>
                </ListItem>
                <ListItem disablePadding divider={true}>
                    <ListItemButton onClick ={handleLogout}>
                        <ListItemIcon>
                            <LogoutIcon />
                        </ListItemIcon>
                        <ListItemText primary="Log Out" />
                    </ListItemButton>
                </ListItem>
            </List>
        </Box>
    )
}

export default SideMenu