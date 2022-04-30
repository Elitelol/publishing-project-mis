import {Navbar, NavItem, NavLink} from "react-bootstrap";
import {Link, useNavigate} from "react-router-dom";
import {useContext} from "react";
import {UserContext} from "../auth";
import styled from "styled-components";

const LeftNavContainer = styled.div`
    margin-right: auto;
`;

const Nav = () => {
    const [state, setState] = useContext(UserContext);
    const navigate = useNavigate()

    const handleLogout = () => {
        setState({data: null, loading: true, error: null});
        localStorage.removeItem("token");
        navigate("/");
    }

    return <>
        {state.data && (
            <Navbar>
                <LeftNavContainer>
                    <NavItem>
                        <NavLink onClick = {handleLogout}> Log Out</NavLink>
                    </NavItem>
                    <NavItem> My Account</NavItem>
                    <NavItem> Dashboard </NavItem>
                    <NavItem> Projects </NavItem>
                    <NavItem> Users </NavItem>
                </LeftNavContainer>
            </Navbar>
        )}
    </>

};

export default Nav