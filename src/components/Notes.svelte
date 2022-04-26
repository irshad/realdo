<script>
    import { onMount } from "svelte";
    import { fly } from "svelte/transition";
    import Clipboard from "svelte-clipboard";
    import Toast from "../stores/toast";
    import Modal from "../utils/Modal.svelte";
    import { Filesystem, Directory, Encoding } from '@capacitor/filesystem';

    // icons
    import FileIcon from "./svg/FileIcon.svelte";
    import DownloadIcon from "./svg/DownloadIcon.svelte";
    import TrashIcon from "./svg/TrashIcon.svelte";
    import CopyIcon from "./svg/CopyIcon.svelte";
    import CloseNotesIcon from "./svg/CloseNotesIcon.svelte";

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
        try {
            await Filesystem.writeFile({
                path: 'realdo.txt',
                data: value,
                directory: Directory.Documents,
                encoding: Encoding.UTF8,
            });
            downloadFile = false;
            Toast.success('Download Successful');
        } catch (error) {
            Toast.error(error);
        }
    };

    const openLocalFile = async () => {
        try {
            contents = await Filesystem.readFile({
                path: 'realdo.txt',
                directory: Directory.Documents,
                encoding: Encoding.UTF8,
            });
    
            value = JSON.stringify(contents.data);
            openFile = false;
        } catch (error) {
            Toast.error(error);
        }
    };
</script>

{#if notes}
    <Clipboard text={value} let:copy>
        <div class="main-section" in:fly={{ x:2000, duration: 300 }} out:fly={{ x:3000, duration: 1200 }}>
            <div class="notes-header">
                <div class="system-menu">
                    <button class="menu-btn" title="Open Downloaded Notes" on:click={() => (openFile = !openFile)}>
                        <FileIcon />
                    </button>

                    <button class="menu-btn" title="Download Notes" on:click={() => (downloadFile = !downloadFile)}>
                        <DownloadIcon />
                    </button>
                </div>

                <div class="classic-menu">
                    <button class="menu-btn" title="Clear Text" on:click={() => { clearText(), Toast.warning('Text cleared successfully');}}>
                        <TrashIcon />
                    </button>
    
                    <button class="menu-btn" title="Copy Text to Clipboard" on:click={() => { copy(), Toast.success('Text copied successfully'); }}>
                        <CopyIcon />
                    </button>
                    
                    <button class="menu-btn" title="Close Notes" on:click={() => { notes = !notes }}>
                        <CloseNotesIcon />
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
