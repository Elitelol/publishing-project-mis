import {AppBar, Box, Button, Toolbar, Typography} from "@mui/material";

const Navbar = () => {
    return (
        <Box>
            <AppBar position = "sticky">
                <Toolbar>
                    <Typography variant = "h6">Publishing House Project Management System</Typography>
                </Toolbar>
            </AppBar>
        </Box>
    );
}

export default Navbar