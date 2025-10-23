import React, { useState } from "react";
import { useLocation } from "react-router-dom";
import { useFormik } from "formik";
import * as Yup from "yup";
import {
  Button,
  Form,
  Grid,
  Icon,
  Segment,
  Message,
  Divider,
} from "semantic-ui-react";
import { parseJwt, handleLogError } from "../misc/Helpers";
import { businessApi } from "../misc/BusinessApi";
import resetImg from "../../assets/images/reset-pass.png";
import "./authen.css";
const ValidationSchema = Yup.object().shape({
  password: Yup.string()
    .min(3, "At least 3 characters!")
    .max(35, "Max 35 characters!")
    .required("Required!"),
  confirmPassword: Yup.string()
    .min(3, "At least 3 characters!")
    .max(35, "Max 35 characters!")
    .required("Required!"),
});

function ResetPassword() {
  const location = useLocation();
  const queryParams = new URLSearchParams(location.search);
  const tokenResetPassword = queryParams.get("token");
  console.log("tokenResetPassword", tokenResetPassword);

  const [passwordVisible, setPasswordVisible] = useState(false);
  const [message, setMessage] = useState("");
  const [isError, setIsError] = useState(false);

  const togglePasswordVisibility = () => {
    setPasswordVisible(!passwordVisible);
  };

  const formik = useFormik({
    initialValues: {
      password: "",
      confirmPassword: "",
    },
    validationSchema: ValidationSchema,
    onSubmit: async (values) => {
      console.log(values);

      if (!values.password || !values.confirmPassword) {
        setMessage("Username & password is required!");
        setIsError(true);
        return;
      } else {
        setIsError(false);
      }

      if (values.password !== values.confirmPassword) {
        setMessage("Confirmed password not match!");
        setIsError(true);
        return;
      } else {
        setIsError(false);
      }

      // Xử lý khi form được submit
      try {
        const response = await businessApi.changePassword(
          values.password,
          tokenResetPassword
        );
        console.log("response", response);
        const { accessToken } = response.data;
        console.log("data reset", response.data);
        // const data = parseJwt(accessToken);
        setIsError(false);
      } catch (error) {
        handleLogError(error);
        setMessage("The username or password provided are incorrect!");
        setIsError(true);
      }
    },
  });

  return (
    <div>
      <div className="loginImage text-center">
        <img
          className="formImage"
          src={resetImg}
          width="200"
          style={{ position: "relative" }}
          alt="login"
        />
      </div>

      <div className="mb-2 text-center ">
        {/* <img src={resetPassImg} width="150" style={{ position: 'relative' }} alt="login" /> */}
        <h2>Reset your password ~</h2>
      </div>

      <div className="center">
        <div className="authenForm">
          <Grid textAlign="center">
            <Grid.Column style={{ maxWidth: 360 }}>
              <Form size="large" onSubmit={formik.handleSubmit}>
                <Segment>
                  <div className="reset-password d-flex justify-content-between">
                    <div>
                      <h4>Enter new password</h4>
                    </div>
                    <div className="text-danger">
                      {formik.errors.password ? (
                        <div>{formik.errors.password}</div>
                      ) : null}
                    </div>
                  </div>

                  <Form.Input
                    name="password"
                    type={passwordVisible ? "text" : "password"}
                    icon={
                      <Icon
                        name={passwordVisible ? "eye" : "eye slash"}
                        link
                        onClick={togglePasswordVisibility}
                      />
                    }
                    fluid
                    iconPosition="right"
                    action={{
                      icon: "lock",
                      tabIndex: -1,
                      // color: 'orange'
                      // style: {
                      //     background: '#F2711C',
                      //     opacity: '50%',
                      //     color: 'white'
                      // },
                    }}
                    actionPosition="left"
                    placeholder="New password..."
                    onChange={formik.handleChange}
                    value={formik.values.password}
                  />

                  <div className="reset-password d-flex justify-content-between">
                    <div>
                      <h4>Confirm new password</h4>
                    </div>
                    <div className="text-danger">
                      {formik.errors.confirmPassword ? (
                        <div>{formik.errors.confirmPassword}</div>
                      ) : null}
                    </div>
                  </div>
                  <Form.Input
                    name="confirmPassword"
                    type={passwordVisible ? "text" : "password"}
                    icon={
                      <Icon
                        name={passwordVisible ? "eye" : "eye slash"}
                        link
                        onClick={togglePasswordVisibility}
                      />
                    }
                    fluid
                    iconPosition="right"
                    action={{
                      icon: "lock",
                      tabIndex: -1,
                      // color: 'orange'
                      // style: {
                      //     background: '#F2711C',
                      //     opacity: '50%',
                      //     color: 'white'
                      // },
                    }}
                    actionPosition="left"
                    placeholder="Confirm password..."
                    onChange={formik.handleChange}
                    value={formik.values.confirmPassword}
                  />

                  <Button color="orange" fluid size="large" type="submit">
                    Save
                  </Button>
                </Segment>
              </Form>

              {isError && <Message negative>{message}</Message>}
              <Divider horizontal>have a nice day !</Divider>
            </Grid.Column>
          </Grid>
        </div>
      </div>
    </div>
  );
}

export default ResetPassword;
