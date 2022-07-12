<script>
    import { onMount } from "svelte";
    import Topbar from "../components/Topbar.svelte";
    import Footer from "../components/Footer.svelte";
    import Section from "../components/Section.svelte";
    import Card from "../components/Card.svelte";
    import Placeholder from "../components/Placeholder.svelte";
    import TodoModal from "../components/TodoModal.svelte";
    import Search from "../components/Search.svelte";
    import Tabs from "../components/tabs/Tabs.svelte";
    import Tab from "../components/tabs/Tab.svelte";
    import TabPanel from "../components/tabs/TabPanel.svelte";
    import PullToRefresh from "../components/PullToRefresh.svelte";
    import { AppUpdate } from '@robingenz/capacitor-app-update';
    import Modal from "../utils/Modal.svelte";

    let id = '';
    let todoItem = '';
    let todoList = [];
    let pendingTodoList = [];
    let completedTodoList = [];
    let todoModal;
    let todoModalID = '';
    let todoModalText = '';
    let todoModalStatus = '';
    let showSearch = false;
    let searchValue;
    let searchList = [];
    let tab;
    let timeDate;
    let show = false;

    onMount(async() => {
        if (localStorage.getItem("todoList")) {
            todoList = JSON.parse(localStorage.getItem("todoList"));
        };

        document.addEventListener('swiped-left', function(e) {
            tab = 2;
        });

        document.addEventListener('swiped-right', function(e) {
            tab = 1;
        });

        const currentVersionresult = await AppUpdate.getAppUpdateInfo();
        const availableVersionresult = await AppUpdate.getAppUpdateInfo();

        if(currentVersionresult.currentVersion !== availableVersionresult.availableVersion) {
            show = true;
        }
    });

    $:if(localStorage.getItem("todoList")) {
        completedTodoList = [];
        todoList.forEach((item, index) => {
            completedTodoList = todoList.filter(item => item.status == true);
        });
        
        pendingTodoList = [];
        todoList.forEach((item, index) => {
            pendingTodoList = todoList.filter(item => item.status == false);
        });
    }

    const mongoObjectId = () => {
		id = (new Date().getTime() / 1000 | 0).toString(16);
		id = id + 'xxxxxxxxxxxxxxxx' .replace(/[x]/g, function() {
				return (Math.random() * 16 | 0).toString(16);
		}).toLowerCase();
	};

    const addTodo = () => {
        tab = 1;
        mongoObjectId();
        timeDate = new Date();
		todoList = [...todoList, {id: id, todo: todoItem, timeDate: timeDate, status: false}];
		todoItem = '';
        localStorage.setItem('todoList', JSON.stringify(todoList));
        document.getElementById("tab-panel").scrollTop = document.getElementById("tab-panel").scrollHeight + 80;
    };

    const removeTodoFromList = (index) => {
        let currentTodo = todoList.findIndex(todo => todo.id === index);
		todoList.splice(currentTodo, 1);
		todoList = todoList;
        localStorage.setItem('todoList', JSON.stringify(todoList));
    };

    const removeSearchTodoFromList = (index) => {
        let currentTodo = searchList.findIndex(todo => todo.id === index);
		searchList.splice(currentTodo, 1);
        searchList = searchList;
        removeTodoFromList(index);
    }

    const todoComplete = (index) => {
        let currentTodo = todoList.filter(todo => todo.id === index)[0];
        currentTodo.status = !currentTodo.status;
        todoList = todoList;
        searchList = searchList;
        localStorage.setItem('todoList', JSON.stringify(todoList));
    }

    const openTodo = (index) => {
        todoModal = !todoModal;
        let currentTodo = todoList.filter(todo => todo.id === index)[0];
        todoModalID = currentTodo.id;
        todoModalText = currentTodo.todo;
        todoModalStatus = currentTodo.status;
    };

    const updateTodo = () => {
        todoList.forEach((item, index) => {
            if (item.id === todoModalID) {
                todoList[index].todo = todoModalText;
                todoList[index].status = todoModalStatus;
            }
        });
        localStorage.setItem('todoList', JSON.stringify(todoList));
        todoList = todoList;
        searchList = searchList;
    };

    const search = () => {
        searchList = [];
        if(searchValue != '') {
            todoList.forEach((item) => {
                if (item.todo.toLowerCase().includes(searchValue.toLowerCase()) && searchValue !== '') {
                    searchList.push(item);
                }
            });
        }
        return searchList;
    };

    $:searchValue,(() => {
        if(searchValue == '') {
            searchList = [];
        }
    })();

    const refresh = () => {
        if (localStorage.getItem("todoList")) {
            todoList = JSON.parse(localStorage.getItem("todoList"));
        };
    };

    const openAppStore = async () => {
        await AppUpdate.openAppStore();
    };
</script>

<PullToRefresh on:refresh={refresh}>
    <Topbar on:openSearch={() => showSearch = !showSearch} />
    <Section>
        <Tabs>
            <Tab label="one" bind:tab value="1">Todo</Tab>
            <Tab label="two" bind:tab value="2">Completed</Tab>
        </Tabs>
    
        <TabPanel {tab} value="1">
            {#if pendingTodoList.length >= 1}
                {#each pendingTodoList as item}
                    <Card
                        on:click={() => openTodo(item.id)}
                        on:delete={() => removeTodoFromList(item.id)}
                        on:done={() => todoComplete(item.id)}
                        todoStatus={item.status}
                        text={item.todo}
                        timeDate={item.timeDate}
                        search={false}
                    />
                {/each}
            {:else}
                <Placeholder />
            {/if}
        </TabPanel>
    
        <TabPanel {tab} value="2">
            {#if completedTodoList.length >= 1}
                {#each completedTodoList as item}
                    <Card
                        on:click={() => openTodo(item.id)}
                        on:delete={() => removeTodoFromList(item.id)}
                        on:done={() => todoComplete(item.id)}
                        todoStatus={item.status}
                        text={item.todo}
                        timeDate={item.timeDate}
                        search={false}
                    />
                {/each}
            {:else}
                <Placeholder />
            {/if}
        </TabPanel>
    </Section>
    
    <Footer bind:todoItem on:todo={addTodo} />
    <TodoModal bind:todoModal bind:todoModalText bind:todoModalStatus bind:todoModalID on:updateTodo={updateTodo}/>
    
    <Search bind:showSearch on:search={search} bind:searchValue>
        {#if searchList.length >= 1}
            {#each searchList as item}
                <Card
                    on:click={() => openTodo(item.id)}
                    on:delete={() => removeSearchTodoFromList(item.id)}
                    on:done={() => todoComplete(item.id)}
                    todoStatus={item.status}
                    text={item.todo}
                    timeDate={item.timeDate}
                    search={true}
                />
            {/each}
        {:else}
            <Placeholder/>
        {/if}
    </Search>
</PullToRefresh>

{#if show}
    <Modal>
        <h4>
            App Update Available
        </h4>

        <div class="btn-section">
            <button class="cancel-btn br-8" on:click={() => show=!show}>
                Not now
            </button>
            <button class="delete-btn br-8" on:click={openAppStore}>
                Download
            </button>
        </div>
    </Modal>
{/if}

<style>
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