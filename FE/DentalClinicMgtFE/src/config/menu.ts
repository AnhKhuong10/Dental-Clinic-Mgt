import {
  GridIcon,
  UserCircleIcon,
  CalenderIcon,
  TableIcon,
  ListIcon,
  PageIcon,
  PieChartIcon,
} from "@/icons"
import BoxCubeIcon from "@/icons/BoxCubeIcon.vue"
import PlugInIcon from "@/icons/PlugInIcon.vue"



export const menuByRole = {
  ADMIN: [
    {
      title: "Admin",
      items: [
        { icon: GridIcon,  name: "Dashboard", path: "/admin-dashboard" },
        { icon: UserCircleIcon,  name: "User Management", path: "/user-management" },
        { icon: UserCircleIcon,  name: "Patient Management", path: "/patient-management" },
        { icon: TableIcon, name: "PatientRecord Management", path: "/patient-record-management" },
      ],
    },
  ],
  DOCTOR: [
    {
      title: "Doctor",
      items: [
        { name: "Dashboard", path: "/doctor-dashboard" },
        { name: "Patients", path: "/patients" },
      ],
    },
  ],
  LEADER_NURSE: [
    {
      title: "Leader Nurse",
      items: [
        { name: "Dashboard", path: "/leader-nurse-dashboard" },
        { name: "Materials", path: "/materials" },
      ],
    },
  ],
  NURSE: [
    {
      title: "Nurse",
      items: [
        { name: "Dashboard", path: "/nurse-dashboard" },
        { name: "Support", path: "/support" },
      ],
    },
  ],
  RECEPTIONIST: [
    {
      title: "Receptionist",
      items: [
        { name: "Dashboard", path: "/receptionist-dashboard" },
        { name: "Schedules", path: "/schedules" },
      ],
    },
  ],
}
