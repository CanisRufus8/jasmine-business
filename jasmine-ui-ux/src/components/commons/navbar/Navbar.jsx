import React, { useState } from "react";
import { NavLink, useNavigate } from "react-router-dom";
import "./Navbar.css";
import { HamburgetMenuOpen } from "./Icons";
import silva from "../../../assets/images/jasminee.png";
import { useAuth } from "../../context/AuthContext";

function NavBar() {
  const [click, setClick] = useState(false);

  const handleClick = () => setClick(!click);

  let navigate = useNavigate();

  const { getUser, userIsAuthenticated, userLogout } = useAuth();

  const logout = () => {
    userLogout();
  };

  const enterMenuStyle = () => {
    return userIsAuthenticated() ? { display: "none" } : { display: "block" };
  };

  const logoutMenuStyle = () => {
    return userIsAuthenticated() ? { display: "block" } : { display: "none" };
  };

  const adminPageStyle = () => {
    const user = getUser();
    return user && user.data.rol[0] === "ADMIN"
      ? { display: "block" }
      : { display: "none" };
  };

  const userPageStyle = () => {
    const user = getUser();
    return user && user.data.rol[0] === "USER"
      ? { display: "block" }
      : { display: "none" };
  };

  const getUserName = () => {
    const user = getUser();
    return user ? user.data.sub : "";
  };
  return (
    <>
      <nav className="navbar">
        <div className="nav-container row">
          <div className="col-2 navbar-logo">
            <NavLink to="/login" className="nav-logo">
              <img
                className="logo"
                src={silva}
                width={168}
                style={{ position: "relative" }}
                alt="login"
              />
            </NavLink>
          </div>
          <div className="col-8 d-flex justify-content-center">
            <div>
              <ul className={click ? "nav-menu active" : "nav-menu"}>
                <li className="nav-item">
                  <NavLink
                    // exact
                    to="/add-new"
                    // activeclassName="active"
                    className="nav-links"
                    onClick={handleClick}
                  >
                    ADD PRODUCT
                  </NavLink>
                </li>

                <li className="nav-item">
                  <NavLink
                    // exact
                    to="/"
                    // activeclassName="active"
                    className="nav-links"
                    onClick={handleClick}
                  >
                    USER PRODUCTS
                  </NavLink>
                </li>

                <li className="nav-item">
                  <NavLink
                    // exact
                    to="/admin/products"
                    // activeclassName="active"
                    className="nav-links"
                    onClick={handleClick}
                  >
                    ADMIN PRODUCTS
                  </NavLink>
                </li>

                {/* <li className="nav-item">
                  <NavLink
                    // exact
                    to="/add"
                    // activeclassName="active"
                    className="nav-links"
                    onClick={handleClick}
                  >
                    News
                  </NavLink>
                </li>
                <li className="nav-item">
                  <NavLink
                    // exact
                    to="/contact"
                    // activeclassName="active"
                    className="nav-links"
                    onClick={handleClick}
                  >
                    Contact
                  </NavLink>
                </li> */}
              </ul>
            </div>
          </div>
          <div className="col-2">
            <div className="text-end" style={logoutMenuStyle()}>
              <span className="authen">{`Hi ${getUserName()}`}</span>
              <span className="authen">|</span>
              <NavLink to="/login" className="authen" onClick={logout}>
                <span>Log out</span>
                <i className="fa-solid fa-right-from-bracket"></i>
              </NavLink>
            </div>
            <div className="text-end authen" style={enterMenuStyle()}>
              <NavLink to="/login">
                <span className="authen">Log in</span>
              </NavLink>
              <span className="authen p-2">/</span>
              <NavLink to="/sign-up">
                <span className="authen">Sign up</span>
              </NavLink>
            </div>
            <div className="nav-icon" onClick={handleClick}>
              <span className="icon">
                <HamburgetMenuOpen />
              </span>
            </div>
          </div>
        </div>
      </nav>
    </>
  );
}

export default NavBar;
