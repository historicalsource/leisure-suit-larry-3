;**
;**	Logics for room 420 -- Showroom entrance with maitre d' & telephone
;**
;**	LSL3:  Passionate Patti in Pursuit of the Pulsating Pectorals
;**	       Copyright 1989
;**		by Al Lowe, Bill Skirvin, Carlos Escobar, and Roger Hardy
;**
;**	Last Update:	January 17, 1990
;**
;**


(script#				420)

(public
	rm420				0
)

(include "420.sh")


(procedure
	PrintMD
)


;**		This room is triggered by 260 (when ego gets his knife).  
;**	The maitreD does one of our copy-protection checks.  When he's
;**	present AND you haven't seen the show AND you inquire about
;**	the show, he says you need a ticket.  If "show pass" he says
;**	tonight we're only accepting passes from page #xx.  What
;**	is the number of the pass on that page?  
;**	
;**		After L gives him the right pass #, he indicates he expects a tip.
;**	L can give him the $20 tip any time.  (PutInRoom iMoney)
;**	
;**		MD is here until L SEES the show, when L LEAVES the show and 
;**	when L WAS the show.  He refuses things other than $20+pass.
;**
;**		Cherri is only in the lobby if prevRoom was not 430 and L has seen
;**	her dance and she hasn't told him she wants his land (lawyerState).  
;**	C waits backstage for her deed until L has beenIn440.
;**
;**		If prevRoom is 435 C is standing with L by the phone.
;**	If (lawyerState), then she says she'll wait for him backstage
;**	and walks away from the telephone, goes through the door and
;**	waits backstage.
;**
;**		Stage manager is here if C is not backstage and L is not 
;**	Ziegfield.  If L is Ziegfield, door is unlocked and L can 
;**	"open door" to retreive his clothes from backstage.
;**
;**		Look room must indicate the open/closed state of the showroom
;**	and whether MD and C are present.  


;**		showroomState gets changed in these rooms at these times:
;**	SRpreShow							;**	000 at start of game
;**	SRshowIsOn							;**	260 when L gets knife from T
;**	SRshowDone							;**	420 after C's dance number
;**	SRcherriOnPhone					;**	420 as L leaves 420 after 430
;**	SRknowsAboutDeed					;**	435 when L learns C wants a farm
;**	SRcherriBackstage					;**	422 when C leaves L at the phone
;**	SRstageDoorUnlocked				;**	431 during L's big dance number
;**	SRdone								;**	440 when L removes Ziegfield suit


(synonyms
	(girl cherri dancer showgirl )
	(man maitred doorman bouncer manager guard )
)


(enum												;**	RoomScript state values
	intro
	backFromShow
	egoInPlace
	MDinPlace
	MDspeaks
	aintbad
	startLbackstage
	atStageDoor
	doorIsOpen
	dragQueenIsInside
	doorSlam
	goto440
	knockStageDoor
	faceDoor
	youBroughtHer
	knockForCherri
	itsMe
	haveUgot
	deedWhatDeed
	comeBackWhenYouDo
	heBroughtIt
	happiestWoman
	openThatDoor
	doorBeOpen
	closeTheDoor
	changeTo440
)


(enum												;**	MaitreDScript state values
	MDintro
	startCopyCheck
	ohReally
	legal
	startMDtalk
	stopMDtalk
	giveMoney
	decisionTime
	enterShowroom
	openRope
	moveLarry
	centeredOnOpening
	goto430
	pirate
	UBdying
)


(local
	[string  166] 
	[string2 22]
	pageNum
	passNum
	passNumbers =	[	00000					;	Page # 
							00000
							00000
							00741					;**	3
							00000
							55811					;**	5
							30004					;**	6
							00000 
							00000 
							18608					;**	9

							25695					;**	10
							32841 				;**	11
							00993					;**	12
							00000
							00000 
							09170					;**	15
							00000 
							00000 
							49114					;**	18
							33794					;**	19

							00000
							00000
							54482					;**	22
							62503					;**	23
							00000
							00000
							00000
						]
)


(instance rm420 of Room
	(properties
		picture		420
		east			415
	)


	(method (init)
		(Load SOUND sDoorClosing)

		(if (ego has iDeed)
			(Load VIEW iDeed)
		)

		(super init)

		(addToPics
			add 				atpPoster1,
			add 				atpPoster2,
			add 				atpPhone,
			add				atpPodium,
			doit
		)

		(aRope  				init)
		(aDoor				init)

		(NormalEgo)
		(ego	
			observeControl cYellow cLtMagenta,
			init,
		)

		(self
			setScript 		RoomScript
		)

		(if (or	(== prevRoomNum 430)
					(== prevRoomNum 431)
					(== showroomState SRshowIsOn))
			(aMaitreD
				view				vMaitreD,
				setPri			5,
				setLoop			lMDtalking,
				init,
			)
		)

		(if (< showroomState SRknowsAboutDeed)
			(aManager			init)
			(= someObject aDoor)
		)

		(if (or	(== showroomState SRshowDone)
					(== showroomState SRcherriOnPhone)
					(== showroomState SRknowsAboutDeed))
			(aCherri				init)
			(= someObject aDoor)
		)

		(cond
			((== prevRoomNum 440)			;**	from backstage 
				(if (== egoState BACKSTAGE)
					(= egoState NORMAL)
				)
				(TheMenuBar draw)
				(StatusLine enable)
				(ego 
					loop				facingEast,
					posn 				44 142,
				)
			)

			((== prevRoomNum 435)			;**	coming back from big face 
				(= egoState NORMAL)
				(ego
					loop				prevLoop,
					posn				(if prevRoomX prevRoomX else 99)
										(if prevRoomY prevRoomY else cherriY),
				)
				(aCherri
					init,
				)
			)

			((== prevRoomNum 430)			;**	coming back from Cherri's show
				(RoomScript changeState backFromShow)
			)

			((== prevRoomNum 431)			;**	L just finished his big number
				(self style IRISOUT)
				(RoomScript changeState backFromShow)
			)

			(( > (ego y) 130)
				(ego 
					posn 				317 188,
					loop				facingWest,
				)
			)

			(else	  								;**	coming in from anywhere else
				(ego 	
					loop				facingWest,
					posn				309 163,
				)
			)
		)
	)


	(method (newRoom n)
		(if (== someObject aDoor)
			(= someObject FALSE)
			(DisposeScript 421)
			(DisposeScript 422)
		)

		(super newRoom n)
	)
)


(instance RoomScript of Script
	(method (handleEvent event)
		(if (or	(!= (event type) saidEvent) (event claimed)) (return))

		(cond
			((Said 'look/picture, painting')
				(Print "The posters on the wall are advertising the current
					showroom headliner,
					the lovely exotic dancer,
					Ms. Cherri Tart!")
			)

			((or	(Said 'make/call')
					(Said 'telephone')
					(Said 'use/phone'))
				(if (== showroomState SRcherriOnPhone)
					(Print "It's in use, Larry!")
				else
					(Print "This telephone is only usable by those with
						correct change!")
				)
			)

			((Said '/change')
				(Print "There is none.")
			)

			((Said 'unlock/door')
				(Print "You have no key to fit this door.
					Have you tried knocking?")
			)

			((Said 'open/door')
				(cond
					((not nearStageDoor)
						(NotClose)
					)
					((== egoState WEARING_ZIEGFIELD)
						(self changeState startLbackstage)
					)
					(else
						(Print "You would, but it's locked.")
					)
				)
			)

			((and (== egoState WEARING_ZIEGFIELD)
					(Said '/clothes, suit'))
			 	(Print "Where did you leave your clothes?")
			)

			((Said 'knock')
				(cond
					((== egoState WEARING_ZIEGFIELD)
					 	(Print "Why not just go on in? It appears to be open.")
					)
					((or	(TstFlg beenIn440)
							(>= showroomState SRdone))
						(Print "You have no further business backstage!")
					)
					((not nearStageDoor)
						(NotClose)
					)
					((== showroomState SRshowDone)
						(Print "As you begin to knock, 
							you hear something backstage.")
					)
					(else
						(self changeState knockStageDoor)
					)
				)
			)

			((Said 'look<in/keyhole, (hole<key), door')
				(cond
					((not nearStageDoor)
						(NotClose)
					)
					(else
						(Print "It looks black in there.")
					)
				)
			)

			((Said 'look, read/sign, door')
				(cond
					((not nearStageDoor)
						(NotClose)
					)
					(else
						(Print "A tiny sign on the door reads,
							\"Stage Door_-- Authorized Personnel Only\"")
					)
				)
			)

			((Said 'enter/showroom')
				(Print "There IS a way.")
			)

			((Said '/rope')
				(Print "The rope is there to keep out all those without
					a ticket.")
			)

			((Said 'look>')
				(cond
					((Said '/podium')
						(if (== showroomState SRcherriOnPhone)
							(Print "The showroom's maitre d' waits for additional
								customers.")
						else
							(Print "The podium is made of rosewood and plastic
								laminates.")
						)
					)

					((Said '/showroom')
						(Print "Through the doorway lies the casino's showroom.
							You could see it better from the inside.")
					)

					((Said '/wall')
						(Print "The posters on the wall are advertising the current
							showroom headliner,
							the lovely exotic dancer,
							Ms. Cherri Tart!")
						(Print "There is a public telephone on the
							wall between the stage door and the showroom
							entrance.")
					)

					((Said '/phone')
						(cond
							((== showroomState SRcherriOnPhone)
								(Print "A gorgeous showgirl is talking on the_--
									wait a minute! 
									It's Cherri, the tart, er, uh,
									dancer from the showroom!")
							)
							(else
								(Print "There is a public telephone on the
									wall between the stage door and the showroom
									entrance.")
							)
						)
					)

					((Said '[/room]')
						(Print (Format @string
							"The outer lobby of the Casino's Showroom is basically
							empty, except for %s."
								(cond
									((== showroomState SRcherriOnPhone)
										{a gorgeous woman in a dressing gown}
									)
									((cast contains aMaitreD)
										{a man standing behind a podium}
									)
									(else
										{you}
									)
								)
							)
						)
					)
				)
			)
		)
	)

	(method (changeState newState)
		(ShowState self newState 1 green)

		(switch (= state newState)

			(intro
			)


			;**	L's just coming back from the showroom; handle 430 & 431.

			(backFromShow
				(TheMenuBar draw)
				(StatusLine enable)
				(aRope
			  		setCel			LastCel,
					stopUpd,
				)
				(aMaitreD
					view				vMaitreD,
				 	posn				125 109,
					setPri			5,
					init,
				)
				(NormalEgo facingSouth)
				(HandsOff)
				(ego
					illegalBits		0,
					ignoreActors	TRUE,
					posn				150 94,
					setMotion		MoveTo 150 114 self,
				)
			)

			(egoInPlace
				(ego 					stopUpd)
				(aRope
					setCycle			BegLoop,
				)
				(aMaitreD
					setLoop			lMovingRight,
					setCycle			Walk,
					setMotion		MoveTo MaitreDX MaitreDY self,
				)
			)

			(MDinPlace
				(aMaitreD
					setLoop			lMDtalking,
					setCycle			Forward,
				)
				(delay 3)
			)

			(MDspeaks
				(aMaitreD
					setCel			0,
					stopUpd,
				)
				(aRope
					stopUpd,
				)
				(NormalEgo)
				(ego
					observeControls	cYellow cLtMagenta,
				)
				(if (== prevRoomNum 430)
					(Print "\"What a show, eh, buddy?\" slurps the maitre d'.
						\"And how about that Cherri Tart?\"")
				else
					(Print "The maitre d' wisecracks,
						\"You seem to have been a big hit with the audience!
						Five hundred one-dollar bills ain't bad...
						for your first night!\"")
					(delay 2)
				)
			)

			(aintbad
				(Print "You think, \"Just imagine:
					I got to have all that fun and make money, too!\"")
				(Print "(Why, that was even better than being a programmer for
					Sierra On-Line!)" LOW)
			)


			;**	Go on in!

			(startLbackstage
				(Ok)
				(HandsOff)
				(ego
					illegalBits		0,
					ignoreActors,
					setMotion		MoveTo 44 140 self,
				)
			)

			(atStageDoor
				(aDoor
					setCycle			EndLoop self,
				)
				(ego
					setLoop			facingLeft,
				)
			)

			(doorIsOpen
				(ego
					setMotion		MoveTo -3 140 self ,
				)
			)

			(dragQueenIsInside
				(aDoor				
					setCycle			BegLoop self,
				)
			)

			(doorSlam
				(soundFX
					number			sDoorClosing,
					loop				1 ,
					play ,
				)
				(= cycles 12)
			)

			(goto440
				(curRoom newRoom 440)
			)


			;**	Handle knock on stage door and die...

			(knockStageDoor
				(HandsOff)
				(aCherri setScript FALSE)
				(ego
					illegalBits		0,
					ignoreActors,
					setMotion		MoveTo 50 143 self,
				)
			)

			(faceDoor
				(ego
					loop				facingWest,
				)
				(= cycles 11)
			)

			(youBroughtHer
				(if (== showroomState SRcherriBackstage)
					(Print "Daring to defy the \"Authorized Personnel Only\"
						sign on the stage door,
						you softly knock one, two, three times.
						You hope Cherri is true to her word!")
					(delay 3)
				else
					(delay 0)
					(= cycles 0)
					((aManager script) cue)
					;**	let his script take over from here
					(self dispose)
				)
			)


			;**	Handle knocking with the deed

			(knockForCherri
				(Print "A small voice whispers through the door...")
				(Print "\"Larry?\"")
				(Print "\"Is that you?\"")
				(delay 3)
			)

			(itsMe
				(Print "You think to yourself,
					\"Thank heavens, it's Cherri!\"")
				(Print "You whisper through the door,
					\"Yes, Cherri, it's me, Larry; Larry Laffer!\"")
				(delay 3)
			)

			(haveUgot
				(Print "\"Oh, Larry, I've been waiting for your return!
					Did you get the deed?\"")
				(if (ego has iDeed)
					(= state (- heBroughtIt 1))
				)
				(delay 3)
			)

			(deedWhatDeed
				(Print "\"Uh, no, ah, er,\" you stammer,
					\"I don't have it.\"")
				(delay 3)
			)

			(comeBackWhenYouDo
				(Print "\"Well, when you do find it, come back,\" says Cherri.
					\"But don't take too long, ok?
					I've got another show to do tonight.\"")
				(Print "\"Ok, I'll try,\" you conclude.")
				(NormalEgo)
				(ego
					observeControls	cYellow cLtMagenta,
				)
			)


			;**	Larry came with his deed...

			(heBroughtIt
				(Print "\"I've got it right here, sweetheart,\" you respond,
					\"and I think you know just how much I'd like to
					give it to you!
					This is your big chance!
					Now you can give up show business and move to your own
					little place out in the country.\""
						#icon iDeed 0 0)
				(PutInRoom iDeed)
				(theGame changeScore 25)
				(delay 3)
			)

			(happiestWoman
				(Print "\"Oh, Larry!
					640 acres of virgin Nontoonyt rain forest,
					perfectly suited for slash burning and typical
					agricultural pillage.
					You've just made me the happiest woman on Earth!
					Come on backstage and allow me to properly
					express my appreciation.\""
					BIG)
				(delay 3)
			)

			(openThatDoor
				(aDoor
					setCycle			EndLoop self,
				)
			)

			(doorBeOpen
				(aDoor				stopUpd)
				(ego
					setMotion		MoveTo -20 (ego y) self,
				)
			)

			(closeTheDoor
				(aDoor
					setCycle			BegLoop self
				)
			)

			(changeTo440
				(soundFX
					number			sDoorClosing,
					loop				1 ,
					play ,			
				)
				(cls)
				(Print "\"Heh, heh, heh,\" you chuckle.
					\"It looks like I'm gonna get a little something 
					for Kalalau's worthless farmland after all!\"")
				(curRoom newRoom 440)
			)
		)
	)
)


(instance aMaitreD of Actor
	(properties
		view				vMaitreD
		x					MaitreDX
		y					MaitreDY
		loop				lMDtalking
		illegalBits		0
	)

	(method (init)
		(super init)
		(self
			setScript		MaitreDScript,
			ignoreActors,
			stopUpd,
		)
	)
)

(instance MaitreDScript of Script
	(method (handleEvent event &tmp theObj)
		(if (or	(!= (event type) saidEvent) (event claimed)) (return))

		(cond
			((or	(Said 'ask/pass')
					(Said 'ask/about/pass'))
				(cond
					((!= egoState NORMAL)
						(NotNow)
					)
					((not nearMaitreD)
						(Print "He'll never hear you from here.")
					)
					(else
						(Print "\"So where can I get one of those free passes?\"
							you ask the maitre d'.")
						(Format @string "\"Did you look in the box that held
							your disks?\"")
						(self changeState startMDtalk)
					)
				)
			)

			((and (not egoIsPatti)
					(Said '/patti'))
				(cond
					((!= egoState NORMAL)
						(NotNow)
					)
					((not nearMaitreD)
						(Print "He'll never hear you from here.")
					)
					(else
						(Print "\"Do know where I could find Passionate Patti's
							piano bar?\"")
						(Format @string "\"Certainly. You took a wrong turn at
							the top of the staircase. Go right instead of left.\"")
						(self changeState startMDtalk)
					)
				)
			)

			((or	(Said '/casino, gambling')
					(Said 'gamble')
					(Said '//casino, gambling'))
				(cond
					((!= egoState NORMAL)
						(NotNow)
					)
					((not nearMaitreD)
						(Print "He'll never hear you from here.")
					)
					(else
						(Print "\"What kind of place is this?\"
							you ask the maitre d'.
							\"How does a guy do any gambling around this
							casino?\"")
						(Format @string "\"If you knew how Natives, Inc. has
							set the odds here, you wouldn't ask!\"")
						(self changeState startMDtalk)
					)
				)
			)

			((or	(Said 'enter/showroom')
					(Said 'ask/show')
					(Said 'ask//show')
					(Said 'see/show'))
				(cond
					((!= egoState NORMAL)
						(NotNow)
					)
					((not nearMaitreD)
						(Print "He'll never hear you from here.")
					)
					((>= showroomState SRstageDoorUnlocked)
						(Print "\"You've done quite enough in that room, fella!\"")
					)	
					((>= showroomState SRshowDone)
						(Print "\"Just couldn't get enough that first time, eh?\"")
					)	
					(else
						(Format @string "The maitre d' responds, 
							\"If you would like to see tonight's show 
							you'll have to have a ticket.
							Of course, some of the local tourist magazines 
							contain free passes.
							If you have no ticket, you might try one of those.\"")
						(self changeState startMDtalk)
					)
				)
			)

			((Said 'hand, use, give, show/ticket, magazine')
				(cond
					((!= egoState NORMAL)
						(NotNow)
					)
					((not nearMaitreD)
						(Print "He'll never see it from here.")
					)
					((>= showroomState SRstageDoorUnlocked)
						(Print "\"You've done quite enough in that room, fella!\"")
					)	
					((>= showroomState SRshowDone)
						(Print "\"Just couldn't get enough that first time, eh?\"")
					)	
					(else
						(self changeState startCopyCheck)
					)
				)
			)

			((or	(Said 'hand, give, show/money, ones, bill')
					(Said 'pay, tip/man')
					(Said 'hand, give, show/man/money, ones, bill'))
				(cond
					((!= egoState NORMAL)
						(NotNow)
					)
					((not (ego has iMoney))
						(Print "You're broke.")
					)	
					((not nearMaitreD)
						(Print "He'll never see it from here.")
					)
					((>= showroomState SRstageDoorUnlocked)
						(Print "\"Thanks anyway, fella!\"")
					)	
					(else
						(self changeState giveMoney)
					)
				)
			)

			((Said 'talk/man')
				(cond
					((!= egoState NORMAL)
						(NotNow)
					)
					((not nearMaitreD)
						(Print "No one is near you.")
					)
					((>= showroomState SRstageDoorUnlocked)
						(Print "\"You've done quite enough in that room, fella!\"")
					)	
					((>= showroomState SRshowDone)
						(Print "\"Just couldn't get enough that first time, eh?\"")
					)	
					(else
						(Printf "\"Hello, sir\" you say to the maitre d'.
							%s" laffer)
						(Print "\"Could you give me some information
							about the show inside?\"")
						(Format @string "The maitre d' responds, 
							\"If you would like to see tonight's show 
							you'll have to have a ticket.
							Of course, some of the local tourist magazines 
							contain free passes.
							If you have no ticket, you might try one of those.\"")
						(self changeState startMDtalk)
					)
				)
			)

			((Said 'look/man')
				(Print "The maitre d' looks like he would
					be the life of the party...")
				(Print "(...if the party was an morticians' convention!)" LOW)
			)

			((Said 'give>')
				(= theObj (inventory saidMe))
				(event claimed FALSE)
				(cond
					((not nearMaitreD)
						(Print "Move closer to the maitre d'.")
					)
					((Said '[/noword]')
						(Print "What did you want to give him?")
					)
					((not theObj)
						(Print "It's not yours to give.")
					)
					((not	(theObj ownedBy ego))
						(DontHave)
					)
					((== theObj (inventory at iKnife))
						(Print "\"Hey!\" shouts the maitre d'.
							\"Put that knife away before I call security!!\"")
					)
					((== theObj (inventory at iGrass))
						(Print "\"Get those weeds out of here!\" 
							shouts the maitre d'.")
					)
					((== theObj (inventory at iWood))
						(Print "\"Isn't that precious?\" he snarls.
							\"A hunk of wood. Indeed!\"")
					)
					((== theObj (inventory at iOrchids))
						(Print "\"No, I'm not going to be your date for
							the prom!\"")
					)
					((== theObj (inventory at iCreditCard))
						(Print "\"I'm sorry, sir, but we don't accept credit
							cards here.  Especially not from deadbeats!\"")
					)
					(else
						(Print "The maitre d' replies, 
							\"And just exactly what am I supposed to do with 
							that?\"")
					)
				)
				(event claimed TRUE)
			)
		)
	)


	(method (changeState newState &tmp [theLine 5])
		(ShowState self newState 2 green)

		(switch (= state newState)

			(MDintro
			)


			;**	Handle the copy protection check...

			(startCopyCheck
				(Ok)
				(HandsOff)
				(Print "You say, \"Excuse me, sir, 
					but I'd like to see tonight's show.
					I have my copy of `Nontoonyt Tonight' right here.\"")
				(aMaitreD
					setCycle 	Forward,
				)
				(delay 3)
			)

			(ohReally
				(while (== 0 [passNumbers pageNum])
					(= pageNum (Random 1 24))
				)
				(Format @string "\"Oh, really?
					Well, for tonight's show I am allowed to accept free passes 
					only from our ad on page %d.
					What is the number of the pass on that page?\"" pageNum)
				(PrintMD vMaitreD lFaceIcon 0)
				(= theLine 0)
				(GetInput @theLine 7 {My pass number is:})
				(= passNum (ReadNumber @theLine))
				(if (!= passNum [passNumbers pageNum])	
					(= state (- pirate 1))
				)
		
				(if debugging
					(Printf "You typed %d; I wanted %d from page %d." 
					passNum [passNumbers pageNum] pageNum)
				)

				(Format @string "\"Let me look through this list of passes and
					numbers...\"")
				(PrintMD vMaitreD lFaceIcon 0)
				(delay 3)
			)


			;**	Ok, he's legal.

			(legal
				(aMaitreD
					setCel			0,
					stopUpd,
				)
				(SetFlg passedSRcopyCheck)
				(if (not (TstFlg tippedMaitreD))
					(Format @string
						"\"Well, well. Very good, sir,\" says the maitre d',
						his tone and mannerisms changing entirely.
						\"But, I must have made a mistake.
						It appears there are no more seats available for this show!
						I'm so, so sorry.
						But, perhaps something will open up later.\"")
					(handIcon
						view			vMaitreD ,
						loop			lHandOut ,
					)
					(Print @string
						HIGH
						#title		{the Maitre d' says}
						#mode			teJustCenter
						#icon			handIcon
					)
					(= string 0)
					(HandsOn)
				else
					(Format @string "\"Say,\" he exclaims, 
						\"I remember you.
						Why look here; I must have overlooked this seat right
						here in the front of the house.
						Please, walk this way!\"")
					(PrintMD vMaitreD lFaceIcon cSmile)
					(self changeState enterShowroom)
				)
			)


			;**	Talk a little

			(startMDtalk
				(HandsOff)
				(aMaitreD
					setLoop			lMDtalking,
					setCycle			Forward,
				)
				(delay 3)
			)

			(stopMDtalk
				(aMaitreD
					setCel			0,
					stopUpd,
				)
				(if (not string)
					(Format @string "\"Yes? May I help you sir?\"")
				)
				(PrintMD vMaitreD lFaceIcon 0)
				(HandsOn)
			)


			;**	Give the maitre d' his $20 tip

			(giveMoney
				(HandsOff)
				(aMaitreD
					setLoop			lMDtalking,
					setCycle			Forward,
				)
				(= cycles 0)
				(delay 3)
			)

			(decisionTime
				(aMaitreD
					setCel			0,
					stopUpd,
				)
				(cond
					((== ((Inventory at iMoney) view) ivOnes)
						(Print "\"I don't want your stupid one-dollar bills!\"")
						(PrintMD vMaitreD lFaceIcon 0)
					)
					((not (TstFlg passedSRcopyCheck))
						(Format @string 
							"\"Why thank you very much, sir!
							I'll try to keep your generosity in mind.
							Unfortunately, you must have a ticket or one of the
							casino's complimentary passes in order 
							to gain admittance here.
							Do you have such a pass?
							If so, now is the time to show it to me.\"")
						(PrintMD vMaitreD lFaceIcon 0)
						(PutInRoom iMoney)
						(theGame changeScore 50)
						(SetFlg tippedMaitreD)
						(HandsOn)
					)
					(else							;**	Let Larry enter showRoom
						(HandsOff)
						(Format @string
							"\"Why, thank you very much, sir,
							Well, well, look at this!
							I seem to have found a nice, front row seat
							available here.
							Allow me to show you to your seat!\"")
						(PrintMD vMaitreD lFaceIcon 1)
						(PutInRoom iMoney)
						(SetFlg tippedMaitreD)
						(theGame changeScore 50)
						(delay 2)
					)
				)
			)

			(enterShowroom
				(aMaitreD
					illegalBits		0,
					ignoreActors	TRUE,
					setLoop			lMovingLeft,
					setCycle			Forward,
					setMotion		MoveTo 162 105 self,
				)
			)

			(openRope
				(aMaitreD
					setMotion		MoveTo 125 109 self,
				)
				(aRope
					cycleSpeed		1,
					setCycle			EndLoop,
				)
			)

			(moveLarry
				(aRope				stopUpd)
				(aMaitreD
					setLoop			lMDtalking,
					setCel			0,
					stopUpd,
				)
				(ego
					illegalBits		0,
					ignoreActors,
					setMotion		MoveTo 149 114 self,
				)
			)

			(centeredOnOpening
				(ego
					setMotion		MoveTo 149 94 self,
				)
			)

			(goto430
				(curRoom newRoom 430)
			)


			;**	he's a pirate!

			(pirate
				(aMaitreD
					setCel			0,
					stopUpd,
				)
				(Format @string "\"I'm sorry, sir,
					but that's not the right number.
					Next time, obtain a proper pass in advance!
					Wait right there while I summon security and have
					you arrested for attempting to pass a counterfeit showroom
					pass.\"")
				(PrintMD vMaitreD lFaceIcon 0)
				(delay 2)
			)

			(UBdying
				(Format @string 
					"\"By the way, have you saved your game recently?\"")
				(PrintMD vMaitreD lFaceIcon 0)
				(theGame setScript (ScriptID DYING))
				((ScriptID DYING) 
					caller		vLdeadCopyCheck,
					register 	(Format @string "Next time,
										better have a copy of \"Nontoonyt Tonight\"
										before playing this game."),
					next			(Format @string2 "Al says, \"Read Your Manual!\""),
				)
			)
		)
	)
)


(instance atpPhone of PicView
	(properties
		view					vRoom
		loop					lPhone
		cel					cPhone
		x						70
		y						96
		priority				7
	)
)
(instance atpPoster1 of PicView
	(properties
		view					vRoom
		loop					lPoster
		cel					cPoster1
		x						224
		y						87
		priority				7
	)
)
(instance atpPoster2 of PicView
	(properties
		view					vRoom
		loop					lPoster
		cel					cPoster2
		x						279
		y						93
		priority				7
	)
)

(instance atpPodium of PicView
	(properties
		view					vRoom
		loop					lPodium
		x						166
		y						109
		signal				ignrAct
		priority				6
	)
)

(instance aRope of Prop
	(properties
		view					vRoom
		loop					lRope
;		cel					0
		x						106
		y						91
		cycleSpeed			1
		signal				ignrAct
	)

	(method (init)
		(super init)
		(self
			setPri			6,
			stopUpd,
		)
	)
)
(instance aManager of Prop
	(properties
		view				vStageManager
;		loop				lTalking
		x					1000
		y					1000
	)

	(method (init)
		(super init)
		(self
			setScript		(ScriptID 421),
			stopUpd,
		)
	)
)
(instance aDoor of Prop
	(properties
		view				vRoom
;		loop				lDoor
;		cel				0
		x					42
		y					132
		signal			ignrAct
	)

	(method (init)
		(super init)
		(self
			setPri			9,
			stopUpd,
		)
	)
)

(instance aCherri of Actor
	(properties
		view					vCherri
		loop					facingWest
;		cel					0
		x						onPhoneX
		y						cherriY
		illegalBits			0
	)

	(method (init)
		(super init)
		(self
			setScript		(ScriptID 422),
		)
	)
)

(procedure (PrintMD v l c)
	(Print @string
		HIGH
		#title		{the Maitre d' says}
		#mode			teJustCenter
		#icon			v l c
	)
	(= string 0)
)
(instance handIcon of DCIcon)

