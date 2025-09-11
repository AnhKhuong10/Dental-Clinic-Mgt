import type { Page } from "./page";
import type { Role } from "./role";

export interface User{
    userId: number;
    username: string;
    fullName: string;
    password: string;
    birthdate: Date;
    phone: string;
    email: string;
    enabled: boolean;
    salary: number;
    role: Role;
}

export type UserPage = Page<User>

export interface CreateUserInput{
    username: string;
    password: string;
    rePassword: string;
    fullName: string;
    birthdate: string;
    phone: string;
    email: string;
    salary: number;
    enable: boolean;
    roleId: number;
}

export interface UserDTO{
    userId: number;
    username: string;
    fullName: string;
    email: string;
    phone: string;
    birthdate: string;
    salary: number;
    enabled: boolean;
    roleName: string;
}

export interface UpdateTeacherInput extends CreateUserInput{
    id: number;
}