import {Navbar, NavItem, NavLink} from "react-bootstrap";
import {Link} from "react-router-dom";
import {useContext} from "react";
import {UserContext} from "../auth";
import {stat} from "fs";

const Nav = () => {
    const [state, setState] = useContext(UserContext);
    console.log(state);
    return <Navbar>
        <NavItem>
            <Link to={"/"} className="nav-link">
                Log
            </Link>
        </NavItem>
    </Navbar>
};

export default Nav