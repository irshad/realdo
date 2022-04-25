<script>
    import Loader from "./Loader.svelte";
    import Sidenav from "./Sidenav.svelte";
    import { createEventDispatcher } from "svelte";
    import Notes from "./Notes.svelte";
    import Toast from "../stores/toast";
    import Modal from "../utils/Modal.svelte";
    import { Browser } from '@capacitor/browser';
    import { Filesystem, Directory, Encoding } from '@capacitor/filesystem';

    const dispatch = createEventDispatcher();

    let show = false;
    let loading = false;
    let notes = false;
    let restoreData = false;

    const refresh = () => {
        loading = !loading;
        show = !show;
        setTimeout(() => {
            loading = !loading;
        }, 1000);
    };

    const openNotes = () => {
        notes = !notes;
        show = !show;
    };

    const backup = async () => {
        await Filesystem.writeFile({
            path: 'realdo.json',
            data: localStorage.getItem("todoList"),
            directory: Directory.Documents,
            encoding: Encoding.UTF8,
        });
        Toast.success('Backup Successful');
    };

    const restore = async () => {
        localStorage.removeItem("todoList");
        const contents = await Filesystem.readFile({
            path: 'realdo.json',
            directory: Directory.Documents,
            encoding: Encoding.UTF8,
        });
        localStorage.setItem("todoList", contents.data);
        restoreData = !restoreData;
        Toast.success('Restore Successful');
    };

    const contactUs = async () => {
        await Browser.open({ url: 'https://forms.gle/KYKBrJF3Yhge5TXa6' });
    };
</script>

<header>
    <h1>
        RealDO
    </h1>
    <div class="menu-left">
        <button class="menu-btn" on:click={() => dispatch('openSearch')}>
            <svg xmlns="http://www.w3.org/2000/svg" width="21" height="21" viewBox="0 0 24 24" fill="none" stroke="#000000" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                <circle cx="11" cy="11" r="8"></circle><line x1="21" y1="21" x2="16.65" y2="16.65"></line>
            </svg>
        </button>
        <button class="menu-btn" on:click={() => show = !show}>
            <svg xmlns="http://www.w3.org/2000/svg" width="21" height="21" viewBox="0 0 24 24" fill="none" stroke="#000000" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                <line x1="3" y1="12" x2="21" y2="12"></line>
                <line x1="3" y1="6" x2="21" y2="6"></line>
                <line x1="3" y1="18" x2="21" y2="18"></line>
            </svg>
        </button>
    </div>
</header>

<Sidenav bind:show>
    <button class="nav-button" on:click={openNotes}>
        <svg xmlns="http://www.w3.org/2000/svg" width="21" height="21" viewBox="0 0 24 24" fill="none" stroke="#000000" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
            <path d="M14 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V8z"></path>
            <polyline points="14 2 14 8 20 8"></polyline>
            <line x1="16" y1="13" x2="8" y2="13"></line>
            <line x1="16" y1="17" x2="8" y2="17"></line>
            <polyline points="10 9 9 9 8 9"></polyline>
        </svg>
        Notes
    </button>
    
    <button class="nav-button" on:click={refresh}>
        <svg xmlns="http://www.w3.org/2000/svg" width="21" height="18" viewBox="0 0 24 24" fill="none" stroke="#000000" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
            <polyline points="1 4 1 10 7 10"></polyline>
            <polyline points="23 20 23 14 17 14"></polyline>
            <path d="M20.49 9A9 9 0 0 0 5.64 5.64L1 10m22 4l-4.64 4.36A9 9 0 0 1 3.51 15"></path>
        </svg>
        Refresh
    </button>

    <button class="nav-button"  on:click={contactUs}>
        <svg xmlns="http://www.w3.org/2000/svg" width="21" height="20" viewBox="0 0 24 24" fill="none" stroke="#000000" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
            <path d="M4 4h16c1.1 0 2 .9 2 2v12c0 1.1-.9 2-2 2H4c-1.1 0-2-.9-2-2V6c0-1.1.9-2 2-2z"></path>
            <polyline points="22,6 12,13 2,6"></polyline>
        </svg>
        Contact
    </button>
    
    <div class="backup-restore-btn">
        <button class="nav-button" on:click={backup}>
            <svg xmlns="http://www.w3.org/2000/svg" width="21" height="20" viewBox="0 0 24 24" fill="none" stroke="#000000" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                <polyline points="16 16 12 12 8 16"></polyline>
                <line x1="12" y1="12" x2="12" y2="21"></line>
                <path d="M20.39 18.39A5 5 0 0 0 18 9h-1.26A8 8 0 1 0 3 16.3"></path>
                <polyline points="16 16 12 12 8 16"></polyline>
            </svg>
            Backup
        </button>
        
        <button class="nav-button" on:click={() => (restoreData = !restoreData)}>
            <svg xmlns="http://www.w3.org/2000/svg" width="21" height="20" viewBox="0 0 24 24" fill="none" stroke="#000000" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                <polyline points="8 17 12 21 16 17"></polyline>
                <line x1="12" y1="12" x2="12" y2="21"></line>
                <path d="M20.88 18.09A5 5 0 0 0 18 9h-1.26A8 8 0 1 0 3 16.29"></path>
            </svg>
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
