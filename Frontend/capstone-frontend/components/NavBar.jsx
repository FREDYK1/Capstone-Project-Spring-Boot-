import Image from "next/image";

const NavBar = () => {
    return (
        <nav className="bg-gradient-to-r from-green-600 to-green-500 p-4 text-white flex justify-between items-center shadow-lg">
            <div className="flex items-center space-x-4">
                <Image src="/turntabl_logo.png" alt="Turntabl Logo" width={40} height={40} />
                <h1 className="text-xl font-bold">Turntabl</h1>
            </div>
            <div className="hidden md:flex space-x-6">
                <a href="#" className="hover:text-green-200 transition-colors">Home</a>
                <a href="#" className="hover:text-green-200 transition-colors">About</a>
                <a href="#" className="hover:text-green-200 transition-colors">Contact</a>
            </div>
        </nav>
    );
}

export default NavBar;