<script>
    import { onMount } from "svelte";
    import { fly } from "svelte/transition";
    import Clipboard from "svelte-clipboard";
    import Toast from "../stores/toast";
    import Modal from "../utils/Modal.svelte";
    import { Filesystem, Directory, Encoding } from '@capacitor/filesystem';

    export let notes = true;
    export let value = '';
    let contents = '';
    let openFile = false;
    let downloadFile = false;

    onMount(() => {
        if(localStorage.getItem('notes')) {
            value = localStorage.getItem('notes');
        }
    });

    const saveNotes = () => {
        localStorage.setItem('notes', value);
    };

    const clearText = () => {
        value = '';
        localStorage.removeItem("notes");
    };

    const downloadNotes = async () => {
        await Filesystem.writeFile({
            path: 'realdo.txt',
            data: value,
            directory: Directory.Documents,
            encoding: Encoding.UTF8,
        });
        downloadFile = false;
    };

    const openLocalFile = async () => {
        contents = await Filesystem.readFile({
            path: 'realdo.txt',
            directory: Directory.Documents,
            encoding: Encoding.UTF8,
        });

        value = JSON.stringify(contents.data);
        openFile = false;
    };
</script>

{#if notes}
    <Clipboard text={value} let:copy>
        <div class="main-section" in:fly={{ x:2000, duration: 300 }} out:fly={{ x:3000, duration: 1200 }}>
            <div class="notes-header">
                <div class="system-menu">
                    <button class="menu-btn" title="Open Downloaded Notes" on:click={() => (openFile = !openFile)}>
                        <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="#000000" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                            <path d="M14 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V8z"></path>
                            <polyline points="14 2 14 8 20 8"></polyline>
                            <line x1="16" y1="13" x2="8" y2="13"></line>
                            <line x1="16" y1="17" x2="8" y2="17"></line>
                            <polyline points="10 9 9 9 8 9"></polyline>
                        </svg>
                    </button>

                    <button class="menu-btn" title="Download Notes" on:click={() => (downloadFile = !downloadFile)}>
                        <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="#000000" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                            <path d="M21 15v4a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2v-4"></path>
                            <polyline points="7 10 12 15 17 10"></polyline>
                            <line x1="12" y1="15" x2="12" y2="3"></line>
                        </svg>
                    </button>
                </div>

                <div class="classic-menu">
                    <button class="menu-btn" title="Clear Text" on:click={() => {
                        clearText(),
                        Toast.warning('Text cleared successfully');
                    }}>
                        <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="#000000" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-trash-2">
                            <polyline points="3 6 5 6 21 6"></polyline>
                            <path d="M19 6v14a2 2 0 0 1-2 2H7a2 2 0 0 1-2-2V6m3 0V4a2 2 0 0 1 2-2h4a2 2 0 0 1 2 2v2"></path>
                            <line x1="10" y1="11" x2="10" y2="17"></line>
                            <line x1="14" y1="11" x2="14" y2="17"></line>
                        </svg>
                    </button>
    
                    <button class="menu-btn" title="Copy Text to Clipboard" on:click={() => {
                        copy(),
                        Toast.success('Text copied successfully');
                    }}>
                        <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="#000000" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                            <path d="M16 4h2a2 2 0 0 1 2 2v14a2 2 0 0 1-2 2H6a2 2 0 0 1-2-2V6a2 2 0 0 1 2-2h2"></path>
                            <rect x="8" y="2" width="8" height="4" rx="1" ry="1"></rect>
                        </svg>
                    </button>
                    
                    <button class="menu-btn" title="Close Notes" on:click={() => { notes = !notes }}>
                        <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="#000000" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                            <line x1="18" y1="6" x2="6" y2="18"></line>
                            <line x1="6" y1="6" x2="18" y2="18"></line>
                        </svg>
                    </button>
                </div>
            </div>
            <textarea class="todo-text-area" bind:value on:keyup={saveNotes}/>
        </div>
    </Clipboard>
{/if}

<Modal bind:show={openFile} width="204px">
    <h4>
        Opening file from documents folder will clear notes.
    </h4>

    <div class="btn-section">
        <button class="cancel-btn br-8" on:click={() => openFile=!openFile}>
            Cancel
        </button>
        <button class="delete-btn br-8" on:click={openLocalFile}>
            Open
        </button>
    </div>
</Modal>

<Modal bind:show={downloadFile} width="204px">
    <h4>
        Text file will be downloaded in documents folder.
    </h4>
    
    <div class="btn-section">
        <button class="delete-btn br-8" on:click={() => downloadFile=!downloadFile}>
            Cancel
        </button>
        <button class="cancel-btn br-8" on:click={downloadNotes}>
            Download
        </button>
    </div>
</Modal>

<style>
    .main-section {
        width: 100%;
        height: 100%;
        z-index: 9;
        inset: 0;
        position: absolute;
        background-color: var(--primary-color);
        padding: 16px;
    }

    .notes-header {
        width: calc(100% - 34px);
        margin-bottom: 16px;
        gap: 16px;
        display: flex;
        align-items: center;
        justify-content: space-between;
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

    .menu-btn:hover {
        transform: translate(4px,-4px);
        box-shadow: -4px 4px 0 var(--dark-color);
    }

    .todo-text-area {
        width: calc(100% - 68px);
        height: calc(100% - 122px);
        overflow-y: auto;
        padding: 16px;
        border-radius: 12px;
        border: 2px solid var(--dark-color);
        background-color: var(--light-color);
        font-size: 16px;
        font-weight: 800;
        font-family: 'Roboto Mono', monospace;
        resize: none;
    }

    .system-menu {
        gap: 16px;
        display: flex;
        align-items: center;
        justify-content: flex-start;
    }

    .classic-menu {
        gap: 16px;
        display: flex;
        align-items: center;
        justify-content: flex-end;
    }

    /*  */
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
</style>
