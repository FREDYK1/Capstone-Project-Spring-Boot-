import Image from "next/image";

const NavBar = () => {
    return (
        <nav className="bg-green-500 p-4 text-white flex justify-between items-center">
            <h1>Turntabl</h1>
            <Image src="/turntabl_logo.png" alt="Turntabl Logo" width={50} height={50} />
        </nav> 
    );
}

export default NavBar;