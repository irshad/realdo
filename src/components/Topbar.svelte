<script>
    import Loader from "./Loader.svelte";
    import Sidenav from "./Sidenav.svelte";
    import { createEventDispatcher } from "svelte";
    import Notes from "./Notes.svelte";
    import Toast from "../stores/toast";
    import Modal from "../utils/Modal.svelte";
    import { Browser } from '@capacitor/browser';
    import { Filesystem, Directory, Encoding } from '@capacitor/filesystem';
    
    // icons
    import NotesIcon from "./svg/NotesIcon.svelte";
    import RefreshIcon from "./svg/RefreshIcon.svelte";
    import ContactIcon from "./svg/ContactIcon.svelte";
    import BackupIcon from "./svg/BackupIcon.svelte";
    import RestoreIcon from "./svg/RestoreIcon.svelte";
    import MenuIcon from "./svg/MenuIcon.svelte";
    import SearchIcon from "./svg/SearchIcon.svelte";
import BrowserIcon from "./svg/BrowserIcon.svelte";

    const dispatch = createEventDispatcher();

    let show = false;
    let loading = false;
    let notes = false;
    let restoreData = false;
    let todoList = [];

    const refresh = () => {
        loading = !loading;
        show = !show;
        if (localStorage.getItem("todoList")) {
            todoList = JSON.parse(localStorage.getItem("todoList"));
        };
        setTimeout(() => {
            loading = !loading;
        }, 1000);
    };

    const openNotes = () => {
        notes = !notes;
        show = !show;
    };

    const backup = async () => {
        try {
            await Filesystem.writeFile({
                path: 'realdo.json',
                data: localStorage.getItem("todoList"),
                directory: Directory.Documents,
                encoding: Encoding.UTF8,
            });
            Toast.success('Backup Successful');
        } catch (error) {
            Toast.error(error);
        }
    };
    
    const restore = async () => {
        try {            
            localStorage.removeItem("todoList");
            const contents = await Filesystem.readFile({
                path: 'realdo.json',
                directory: Directory.Documents,
                encoding: Encoding.UTF8,
            });
            localStorage.setItem("todoList", contents.data);
            restoreData = !restoreData;
            Toast.success('Restore Successful');
        } catch (error) {
            Toast.error(error);
        }
    };

    const contactUs = async () => {
        await Browser.open({ 
            url: 'https://forms.gle/KYKBrJF3Yhge5TXa6',
            toolbarColor : "#15aabf",
            windowName: "RealDO"
        });
    };

    const browser = async () => {
        await Browser.open({ 
            url: 'https://google.com',
            toolbarColor : "#15aabf",
            windowName: "RealDO"
        });
    };
</script>

<header>
    <h1>
        RealDO
    </h1>
    <div class="menu-left">
        <button class="menu-btn" on:click={() => dispatch('openSearch')}>
            <SearchIcon />
        </button>
        <button class="menu-btn" on:click={() => show = !show}>
            <MenuIcon />
        </button>
    </div>
</header>

<Sidenav bind:show>
    <button class="nav-button" on:click={openNotes}>
        <NotesIcon />
        Notes
    </button>
    
    <button class="nav-button"  on:click={browser}>
        <BrowserIcon />
        Browser
    </button>
    
    <button class="nav-button" on:click={refresh}>
        <RefreshIcon />
        Refresh
    </button>

    <button class="nav-button"  on:click={contactUs}>
        <ContactIcon />
        Contact
    </button>
    
    <div class="backup-restore-btn">
        <button class="nav-button" on:click={backup}>
            <BackupIcon />
            Backup
        </button>
        
        <button class="nav-button" on:click={() => (restoreData = !restoreData)}>
            <RestoreIcon />
            Restore
        </button>
    </div>
</Sidenav>

<Loader bind:loading />
<Notes bind:notes/>

<Modal bind:show={restoreData} width="204px">
    <h4>
        Restoring backup will overwrites all your exiting todos.
    </h4>
    
    <div class="btn-section">
        <button class="delete-btn br-8" on:click={() => (restoreData = !restoreData)}>
            Cancel
        </button>
        <button class="cancel-btn br-8" on:click={restore}>
            Restore
        </button>
    </div>
</Modal>

<style>
    header {
        height: 64px;
        padding: 0 16px;
        display: flex;
        align-items: center;
        justify-content: space-between;
        border-bottom: 2px solid var(--dark-color);
        background-color: var(--primary-color);
    }

    .menu-left {
        gap: 16px;
        display: flex;
        align-items: center;
        justify-content: flex-start;
    }

    h1 {
        margin: 0;
        font-size: 24px;
        color: var(--dark-color);
        font-weight: 700;
        font-family: 'Roboto Mono', monospace;
    }

    .menu-btn {
        height: 36px;
        width: 36px;
        padding: 0;
        display: flex;
        align-items: center;
        justify-content: center;
        border-radius: 8px;
        border: 2px solid var(--dark-color);
        background-color: var(--light-color);
    }

    .menu-btn:hover, .nav-button:hover {
        transform: translate(4px,-4px);
        box-shadow: -4px 4px 0 var(--dark-color);
    }

    .nav-button {
        height: 40px;
        width: 100%;
        padding: 0 16px;
        font-size: 18px;
        border-radius: 8px;
        gap: 12px;
        display: flex;
        align-items: center;
        justify-content: start;
        background-color: var(--light-color);
        border: 2px solid var(--dark-color);
        font-weight: 600;
        font-family: 'Roboto Mono', monospace;
    }

    h4 {
        margin: 0 0 24px 0;
        text-align: center;
        font-size: 16px;
        font-weight: 700;
        color: var(--dark-color);
        font-family: 'Roboto Mono', monospace;
    }

    .btn-section {
        width: 100%;
        gap: 16px;
        display: flex;
        justify-content: center;
    }

    .btn-section button {
        width: 100%;
        height: 32px;
        border: 2px solid var(--dark-color);
        padding: 0;
        cursor: pointer;
        font-weight: 700;
        font-family: 'Roboto Mono', monospace;
    }

    .cancel-btn {
        background-color: var(--success-color);
    }

    .delete-btn {
        background-color: var(--danger-color);
    }

    .cancel-btn:hover, .delete-btn:hover {
        transform: translate(4px,-4px);
        box-shadow: -4px 4px 0 var(--dark-color);
    }

    .backup-restore-btn {
        gap: 16px;
        display: flex; 
        align-items: center; 
        justify-content: flex-start; 
    }
</style>
